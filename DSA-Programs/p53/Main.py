class Node:
    def __init__(self, name):
        self.name = name
        self.left = self
        self.right = self
        self.up = self
        self.down = self
        self.column = None

class ColumnNode(Node):
    def __init__(self, name):
        super().__init__(name)
        self.size = 0

def cover(column):
    column.right.left = column.left
    column.left.right = column.right
    node = column.down
    while node != column:
        row_node = node.right
        while row_node != node:
            row_node.down.up = row_node.up
            row_node.up.down = row_node.down
            row_node.column.size -= 1
            row_node = row_node.right
        node = node.down

def uncover(column):
    node = column.up
    while node != column:
        row_node = node.left
        while row_node != node:
            row_node.down.up = row_node
            row_node.up.down = row_node
            row_node.column.size += 1
            row_node = row_node.left
        node = node.up
    column.right.left = column
    column.left.right = column

def search(root, solution):
    if root.right == root:
        yield solution[:]
        return

    column = choose_column(root)
    cover(column)

    node = column.down
    while node != column:
        solution.append(node)
        row_node = node.right
        while row_node != node:
            cover(row_node.column)
            row_node = row_node.right

        yield from search(root, solution)

        solution.pop()
        column = node.column
        row_node = node.left
        while row_node != node:
            uncover(row_node.column)
            row_node = row_node.left

        node = node.down

    uncover(column)

def choose_column(root):
    min_size = float('inf')
    selected_column = None
    column = root.right
    while column != root:
        if column.size < min_size:
            min_size = column.size
            selected_column = column
        column = column.right
    return selected_column

def create_dlx_matrix(matrix):
    root = ColumnNode("root")
    columns = []

    for j in range(len(matrix[0])):
        column_node = ColumnNode(str(j))
        columns.append(column_node)
        root.left.right = column_node
        column_node.left = root.left
        column_node.right = root
        root.left = column_node

    for i, row in enumerate(matrix):
        prev = None
        for j, value in enumerate(row):
            if value == 1:
                column_node = columns[j]
                new_node = Node(str(i))
                new_node.column = column_node
                column_node.size += 1

                if prev is None:
                    prev = new_node

                new_node.up = column_node.up
                new_node.down = column_node
                column_node.up.down = new_node
                column_node.up = new_node

                new_node.left = prev
                new_node.right = prev.right
                prev.right.left = new_node
                prev.right = new_node

                prev = new_node

    return root

def exact_cover(matrix):
    dlx_matrix = create_dlx_matrix(matrix)
    solutions = []
    for solution in search(dlx_matrix, []):
        solutions.append([node.name for node in solution])
    return solutions

# Example usage:
matrix = [
    [1, 0, 0, 1, 0, 0, 1],
    [1, 0, 0, 1, 0, 0, 0],
    [0, 0, 0, 1, 1, 0, 1],
    [0, 0, 1, 0, 1, 1, 0],
    [0, 1, 1, 0, 0, 1, 1],
    [0, 1, 0, 0, 0, 0, 1]
]

solutions = exact_cover(matrix)
print("Solutions:", solutions)
