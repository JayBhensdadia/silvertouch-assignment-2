class BlossomGraph:
    def __init__(self, n):
        self.n = n
        self.adjacency = [[] for _ in range(n)]
        self.match = [-1] * n
        self.in_blossom = [-1] * n

    def add_edge(self, u, v):
        self.adjacency[u].append(v)
        self.adjacency[v].append(u)

    def find_augmenting_path(self, start):
        queue = [start]
        visited = [False] * self.n
        parent = [-1] * self.n
        base = [-1] * self.n

        while queue:
            u = queue.pop(0)
            visited[u] = True

            for v in self.adjacency[u]:
                if not visited[v]:
                    queue.append(v)
                    parent[v] = u
                    base[v] = v

                if self.in_blossom[u] != self.in_blossom[v] and base[u] == base[v]:
                    
                    blossom = self.contract(u, v, base)
                    return self.find_augmenting_path(blossom)

        return None  

    def contract(self, u, v, base):
        blossom = set()
        while base[u] != v:
            blossom.add(u)
            self.in_blossom[u] = self.in_blossom[v]
            blossom.add(v)
            self.in_blossom[v] = self.in_blossom[u]

            u = parent[base][u]
            v = parent[base][v]

        blossom.add(u)
        blossom.add(v)
        return blossom

    def augment(self, blossom):
        for u in blossom:
            self.match[u] = -1
            self.in_blossom[u] = -1

        for u in blossom:
            for v in self.adjacency[u]:
                if self.in_blossom[v] != -1 and v not in blossom:
                    self.match[u] = v
                    self.match[v] = u
                    return True  

        return False  

    def maximum_matching(self):
        for u in range(self.n):
            if self.match[u] == -1:
                blossom = self.find_augmenting_path(u)
                if blossom:
                    self.augment(blossom)

        return [(u, v) for u, v in enumerate(self.match) if v != -1]



graph = BlossomGraph(6)
graph.add_edge(0, 1)
graph.add_edge(0, 2)
graph.add_edge(1, 3)
graph.add_edge(2, 3)
graph.add_edge(2, 4)
graph.add_edge(4, 5)

matching = graph.maximum_matching()
print("Maximum Cardinality Matching:", matching)
