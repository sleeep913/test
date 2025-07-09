import numpy as np
import matplotlib.pyplot as plt

# 生成线性可分的二维数据
np.random.seed(0)
num_points = 20
X = np.random.randn(num_points, 2)
Y = np.array([1 if x[0] + x[1] > 0 else 0 for x in X])

# 感知机算法实现
def perceptron(X, Y, lr=0.1, epochs=20):
    w = np.zeros(X.shape[1])
    b = 0
    for _ in range(epochs):
        for xi, yi in zip(X, Y):
            y_pred = 1 if np.dot(w, xi) + b > 0 else 0
            w += lr * (yi - y_pred) * xi
            b += lr * (yi - y_pred)
    return w, b

w, b = perceptron(X, Y)

# 可视化
plt.figure(figsize=(6, 6))
plt.scatter(X[Y==0][:,0], X[Y==0][:,1], color='red', label='类别 0')
plt.scatter(X[Y==1][:,0], X[Y==1][:,1], color='blue', label='类别 1')

# 绘制感知机分界线
x_vals = np.linspace(X[:,0].min()-1, X[:,0].max()+1, 100)
y_vals = -(w[0]*x_vals + b)/w[1]
plt.plot(x_vals, y_vals, 'k--', label='感知机分界线')

plt.xlabel('x1')
plt.ylabel('x2')
plt.legend()
plt.title('感知机分类示例')
plt.grid(True)
plt.show()
