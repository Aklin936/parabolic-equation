import numpy as np
import matplotlib.pyplot as plt

f = open("y.txt", "r")
flag = True
x = []
y = []
f.readline()
while True:
    line = f.readline()
    print(line)
    if not line:
        break
    i = 0
    while line[i]!='|':
        i+=1
    first = float(line[:i])
    second = float(line[i+1:])
    x.append(first)
    y.append(second)
f.close()

x = np.array(x[:3])
y = np.array(y[:3])

plt.plot(x, y)
plt.show()