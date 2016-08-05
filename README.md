

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

### run

Le fichier "run" correspond au fichier .bash executés par INGInious lorsqu'une soumission est envoyée. La première partie est la configuration du test, la deuxième est l'execution du test. Normalement, la deuxième partie est commune à toutes les missions, on peut donc normalement copier-coller cette partie dans les autres dossiers (Faut juste y penser, et penser à une solution pour ne pas devoir faire ça)

### examples

Le dossier "examples" contient des exemples de tests ou d'implémentation, pour pouvoir vérifier si la mission fonctionne correctement. Normalement, y'as une petite description du résultat attendu dans les premières lignes du fichier.
