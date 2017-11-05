import networkx as nx
import matplotlib.pyplot as plt
import numpy as np

"""
Eastern Massachusetts
<NUMBER OF ZONES> 74
<NUMBER OF NODES> 74
<FIRST THRU NODE> 1
<NUMBER OF LINKS> 258
"""
def drawLikeMiterani(G):
    pos=nx.spring_layout(G)
    nx.draw_networkx_nodes(G, pos, node_color='b', alpha=0.15)
    nx.draw_networkx_edges(G, pos, width=1.0)
    nx.draw_networkx_labels(G, pos)
    plt.show()

def drawBarGraph(D, title=''):
    plt.figure()

    plt.bar(range(len(D)), D.values(), 1, color='gray')
    plt.xticks(range(len(D)), list(D.keys()))
    if(len(title)>0):
        plt.title(title)
    plt.show()

def drawFrequencyBarGraph(D, title=''):

    l = list()
    for x in D:
        l.append(D[x])
    print 'Minimum Value', min(l)
    print 'Maximum Value', max(l)
    print 'Average Value', np.average(l)
    bins = np.linspace(0, max(l), 50)
    plt.hist(l, bins=bins, color='red')
    if(len(title)>0):
        plt.title(title)
    plt.show()

#Parsing the data
f =  open("Eastern Massachusetts.txt", "r")
content = f.readlines()
content = [x.strip() for x in content]

# Creating Graph
G = nx.Graph()

for x in content:
    line =  x.split('\t')
    G.add_edge(int(line[0]), int(line[1]), weight=float(line[3]))

#drawLikeMiterani(G)

print 'Number of nodes', G.number_of_nodes()
print 'Number of edges', G.number_of_edges()
print ''
print 'Degree Centrality'
degree_centrality = nx.degree_centrality(G)
'''for x in degree_centrality:
    print x, '->', degree_centrality[x]'''
print ''
#drawBarGraph(degree_centrality, 'Degree Centrality')
#drawFrequencyBarGraph(degree_centrality, 'Degree Centrality Frequency')

print ''
print 'Closeness Centrality'
closeness_centrality = nx.closeness_centrality(G)
'''for x in closeness_centrality:
    print x, '->', closeness_centrality[x]'''
print ''
#drawBarGraph(closeness_centrality, 'Closeness Centrality')
#drawFrequencyBarGraph(closeness_centrality, 'Closeness Centrality Frequency')

print ''
print 'Betweenness Centrality'
betweenness_centrality = nx.betweenness_centrality(G)
'''for x in betweenness_centrality:
    print x, '->', betweenness_centrality[x]'''
print ''
#drawBarGraph(betweenness_centrality, 'Betweenness Centrality')
#drawFrequencyBarGraph(betweenness_centrality, 'Betweenness Centrality Frequency')

print ''
print 'Eigenvector Centrality'
eigenvector_centrality = nx.eigenvector_centrality(G)
'''for x in eigenvector_centrality:
    print x, '->', eigenvector_centrality[x]'''
print ''
#drawBarGraph(eigenvector_centrality, 'Eigenvector Centrality')
#drawFrequencyBarGraph(eigenvector_centrality, 'Eigenvector Centrality Frequency')

'''
print ''
print 'Communicability'
communicability = nx.communicability(G)
for x in communicability:
    print x, '->', communicability[x]
print ''
'''
'''
print ''
print 'Katz Centrality'
katz_centrality = nx.katz_centrality(G, max_iter=100000)
for x in katz_centrality:
    print x, '->', katz_centrality[x]
print ''
#drawBarGraph(katz_centrality, 'Katz Centrality')
#drawFrequencyBarGraph(eigenvector_centrality, 'Eigenvector Centrality Frequency')
'''

#print nx.clustering(G)
