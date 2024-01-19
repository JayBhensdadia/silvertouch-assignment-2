def log_aspect(func):
    def wrapper(*args, **kwargs):
        result = None
        try:
            print(f"Entering {func.__name__}")
            result = func(*args, **kwargs)
            print(f"Exiting {func.__name__}")
        except Exception as e:
            print(f"Error in {func.__name__}: {str(e)}")
        return result

    return wrapper


@log_aspect
def divide(a, b):
    return a / b


@log_aspect
def multiply(x, y):
    return x * y


if __name__ == "__main__":
    result1 = divide(10, 2)
    result2 = multiply(5, 4)
    result3 = divide(8, 0)
