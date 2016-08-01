from subprocess import Popen, PIPE, STDOUT

SUCCESS = "success"
FAILED = "failed"
TIMEOUT = "timeout"
OVERFLOW = "overflow"
CRASH = "crash"

def read_stream(stdout):
    result = ""
    for line in iter(stdout.read, ""):
        result += line
    return result


def feedback_result(problem_id, result):
    Popen(
        [
            "feedback-result",
            "-i", problem_id,
            result
        ]
    )


def feedback_grade(grade):
    Popen(
        [
            "feedback-grade",
            grade
        ]
    )


def feedback_msg(problem_id, msg):
    Popen(
        [
            "feedback-msg",
            "-i", problem_id,
            "-m", msg
        ]
    )


def feedback_append_msg(problem_id, msg):
    Popen(
        [
            "feedback-msg",
            "-i", problem_id,
            "-a",
            "-m", msg
        ]
    )


def feedback_and_exit(problem_id, msg, result):
    feedback_msg(problem_id, msg)
    feedback_result(problem_id, result)
    sys.exit(1)
