## Structure

Chaque dossier correspond à une task sur INGInious; certains dossiers sont des essais de tests, mais n'ont pas été publiés sur INGInious. Ce qui suit ne concerne que les tasks demandant aux étudiants des implémentations de programmes ou des tests de programmes:

Ces tâches demandent à l'étudiant de fournir une implémentation d'une interface:

* m1stack
* m1interpreter
* m3orderedmap
* m4bis
* m5compressor
* m6kruskal

Ces tâches demandent à l'étudiant de fournir une batterie de tests qui seront appliqués sur des implémentations parfois correctes parfois incorrectes:

* m1stacktests
* m1interpretertests
* m3tests
* m4plagiarism_tests
* m5_compressor_tests
* m6_kruskal_tests

Ces tâches demandent des choses divers et variés (QCM, simple réponse, code, ...):

* preexam_1
* preexam_2
* preexam_3
* preexam_heap
* preexam_redblacktree
* preexam_treeqcm

Il existe d'autres dossiers pour d'autres missions, mais ce ne sont que des ébauches, et ne sont pas utilisés ou donnés aux étudiants. (Y'as rien pour la mission 2, par exemple)

### common

Dans le dossier "common/" se trouve des scripts communs à chaque tâche. Le script "build" permet de copier ces scripts dans les différents dossiers des différentes tâches. (Il faut les copier/coller car lorsque INGInious crée le container Docker, celui-ci n'a plus accès au repertoire parent, et donc à "common/")

### run

Chaque tâche contient un fichier "run" qui correspond aux fichier executé par INGInious lors de la soumission d'une tâche. La plupart de ces fichiers "run" utilisent les scripts copiés de "common/".

### examples

Le dossier "examples" contient des exemples de tests ou d'implémentation, pour pouvoir vérifier si la mission fonctionne correctement. Normalement, y'as une petite description du résultat attendu dans les premières lignes du fichier.
