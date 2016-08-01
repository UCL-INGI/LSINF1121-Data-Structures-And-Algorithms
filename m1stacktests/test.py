#!/usr/bin/env python3

from subprocess import Popen, PIPE, STDOUT
from shutil import copyfile
from os import chdir
from ingiapi import feedback_and_exit, feedback_append_msg, OVERFLOW, SUCCESS, FAILED, TIMEOUT, CRASH

def compile_and_run():
    # We compile the stack implement
    result = Popen(["javac", nameTestedStack + ".java"], stderr=STDOUT)
    result.wait()

    if result.returncode != 1:
        feedback_append_msg("stack_test", result.stdout.readlines())

    # We compile and run the tests
    result = Popen(
        [
            "run_student",
            "java",
            "-cp",
            ".:junit-4.12.jar:hamcrest-core-1.3.jar",
            "org.junit.runner.JUnitCore",
            nameTests + ".java"
        ],
        stderr=STDOUT
    )
    result.wait()

    if result.returncode == 252:
        feedback_and_exit(
            "stack_tests",
            "Execution was killed because of an out-of-memory error",
            OVERFLOW
        )
    elif result.returncode == 253:
        feedback_and_exit(
            "stack_tests",
            "Execution timed out",
            TIMEOUT
        )
    elif result.returncode == 254:
        feedback_and_exit(
            "stack_tests",
            "Execution was killed because of a runtime error",
            CRASH
        )

    junit_result = result.stdout.readlines()

    passed = "OK" in junit_result
    return passed

countBuggyTests = 10
nameCorrectStack = "MyStack"
nameTestedStack = "MyStack"
nameWrongStack = "MyBuggyStack"
nameTests = "StackTests"

# We retrieve the tests of the user
result = Popen(["getinput", "stack_tests", "student/" + nameTests + ".java"])
result.wait()

copyfile("junit-4.12.jar", "student/junit-4.12.jar")
copyfile("hamcrest-core-1.3.jar", "student/hamcrest-core-1.3.jar")
copyfile("Stack.java", "student/Stack.java")
chdir("student/")

# We begin by the correct implementation
copyfile(nameCorrectStack + ".java", "student/" + nameTestedStack + ".java")
compile_and_run()

# We test the tests
countOk = 0
countFail = 0

for i in range(0, countBuggyTests):


result = Popen(["true", "-la"], stdout=PIPE)
result.wait()

print(result.returncode)
