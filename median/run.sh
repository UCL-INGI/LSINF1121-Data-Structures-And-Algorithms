#! /bin/bash
export LC_ALL=en_US.UTF-8
export LANG=en_US.UTF-8
export LANGUAGE=en_US.UTF-8

NO_TESTS=6

#produce the output directory
mkdir output

# parse templates
parsetemplate Median.java

# run sur un seul exemple pour les erreurs de compilation etc

javac -cp junit-4.12.jar:. Test.java 2> output/comp.err 

java -cp .:junit-4.12.jar:hamcrest-core-1.3.jar Test 2> output/run.err > output/junit.out


# add files to archive
archive -a Median.java
for f in $(ls output); do
archive -a output/$f
done

# run feedback
if [ -s output/comp.err ]; then # compilation error
	feedback-result failed
	feedback-msg -ae -m "Il y a eu une erreur de compilation : \n"
	cat output/comp.err | rst-code | feedback-msg -a
	exit
fi

if [ -s output/run.err ]; then # error in main()
	feedback-result failed
	feedback-msg -ae -m "Il y a eu une erreur lors de l'exécution : \n"
	cat output/run.err | rst-code | feedback-msg -a
	exit
fi

NO_FAIL=$(wc -l output/junit.out | awk -F" " '{print $1}')
NO_SUCC=$(expr $NO_TESTS - $NO_FAIL)

if [ $NO_FAIL -eq 0 ]; then
	feedback-result success
	feedback-msg -ae -m "Vous avez réussi tout les tests."
else
	feedback-result failed
	feedback-msg -ae -m "Votre programme n'a pas passé tous les tests. Ces tests ont échoués :\n"
	cat output/junit.out | rst-code | feedback-msg -a
fi
