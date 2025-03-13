import matplotlib.pyplot as plt
import numpy as np
import csv

# Чтение данных из CSV файла
x_values = []
y_values = []

with open('output/cos_results.csv', 'r') as file:
    csv_reader = csv.reader(file, delimiter=';')
    next(csv_reader)  # Пропускаем заголовок
    for row in csv_reader:
        x_values.append(float(row[0]))
        y_values.append(float(row[1]))

# Создаем график
plt.figure(figsize=(12, 8))

# Построение точек из CSV файла
plt.scatter(x_values, y_values, color='red', s=30, label='Вычисленные точки')

# Построение непрерывной функции косинуса для сравнения
x_continuous = np.linspace(0, 2*np.pi, 1000)
y_continuous = np.cos(x_continuous)
plt.plot(x_continuous, y_continuous, 'b-', label='cos(x)', alpha=0.7)

# Добавляем линию y = 0
plt.axhline(y=0, color='gray', linestyle='--', alpha=0.7)

# Добавляем линию x = 0
plt.axvline(x=0, color='gray', linestyle='--', alpha=0.7)

# Настройка графика
plt.title('График функции cos(x)')
plt.xlabel('x (радианы)')
plt.ylabel('cos(x)')
plt.grid(True, alpha=0.3)
plt.legend()

# Добавляем метки для особых точек на оси x
x_ticks = [0, np.pi/6, np.pi/4, np.pi/3, np.pi/2, 2*np.pi/3, 3*np.pi/4, 5*np.pi/6, np.pi, 
           7*np.pi/6, 5*np.pi/4, 4*np.pi/3, 3*np.pi/2, 5*np.pi/3, 7*np.pi/4, 11*np.pi/6, 2*np.pi]
x_tick_labels = ['0', 'π/6', 'π/4', 'π/3', 'π/2', '2π/3', '3π/4', '5π/6', 'π', 
                '7π/6', '5π/4', '4π/3', '3π/2', '5π/3', '7π/4', '11π/6', '2π']
plt.xticks(x_ticks, x_tick_labels, rotation=45)

# Устанавливаем пределы осей
plt.xlim(-0.2, 2*np.pi + 0.2)
plt.ylim(-1.2, 1.2)

# Показываем график
plt.tight_layout()
plt.savefig('cos_function_graph.png')  # Сохраняем график в файл
plt.show()
