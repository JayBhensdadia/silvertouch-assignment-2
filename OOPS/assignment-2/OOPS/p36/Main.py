import functools
import logging


class LoggingAspect:
    def __init__(self, logger):
        self.logger = logger

    def log_method_call(self, func):
        @functools.wraps(func)
        def wrapper(*args, **kwargs):
            self.logger.info(
                f"Calling {func.__name__} with args: {args}, kwargs: {kwargs}"
            )
            result = func(*args, **kwargs)
            self.logger.info(f"{func.__name__} returned: {result}")
            return result

        return wrapper


logger = logging.getLogger(__name__)
logging.basicConfig(level=logging.INFO)

aspect = LoggingAspect(logger)


@aspect.log_method_call
def add(a, b):
    return a + b


@aspect.log_method_call
def multiply(a, b):
    return a * b


result_add = add(3, 4)
result_multiply = multiply(3, 4)
