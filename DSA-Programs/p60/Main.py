class Node:
    def __init__(self, sequence):
        self.sequence = sequence
        self.left = None
        self.right = None

def build_wavelet_tree(sequence):
    if len(set(sequence)) == 1:
        
        return Node(sequence)

    node = Node(sequence)
    mid = len(sequence) // 2

    left_sequence = [c for c in sequence[:mid]]
    right_sequence = [c for c in sequence[mid:]]

    node.left = build_wavelet_tree(left_sequence)
    node.right = build_wavelet_tree(right_sequence)

    return node

def rank(node, symbol, index):
    if node is None or index < 0 or index >= len(node.sequence):
        return 0

    count = node.sequence[:index + 1].count(symbol)

    if symbol == '0':
        return count + rank(node.left, symbol, count - 1)
    else:
        return count + rank(node.right, symbol, index - count)

def main():
    input_sequence = "110100101011"
    wavelet_tree = build_wavelet_tree(list(input_sequence))

    
    symbol = '1'
    index = 8
    result = rank(wavelet_tree, symbol, index)
    print(f'The rank of symbol "{symbol}" at index {index} is: {result}')

if __name__ == "__main__":
    main()
