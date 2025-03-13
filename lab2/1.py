import matplotlib.pyplot as plt
import numpy as np

# Данные из файла cscTest.csv
test_x = [0.5235987755982989, 0.7853981633974483, 1.0471975511965976, 1.5707963267948966, 
          2.0943951023931953, 2.356194490192345, 2.6179938779914944, 3.665191429188092, 
          3.9269908169872414, 4.1887902047863905, 4.71238898038469, 5.235987755982989, 
          5.497787143782138, 5.759586531581287]

test_y = [2, 1.4142135623730951, 1.1547005383792515, 1, 1.1547005383792515, 
          1.4142135623730951, 2, -2, -1.4142135623730951, -1.1547005383792515, 
          -1, -1.1547005383792515, -1.4142135623730951, -2]

# Создаем непрерывный график функции csc(x)
x = np.linspace(0.1, 2*np.pi - 0.1, 1000)  # Избегаем точек разрыва
y = 1 / np.sin(x)  # csc(x) = 1/sin(x)

# Создаем график
plt.figure(figsize=(12, 8))

# Построение непрерывной функции
plt.plot(x, y, 'b-', label='csc(x) = 1/sin(x)')

# Построение тестовых точек
plt.scatter(test_x, test_y, color='red', s=50, label='Тестовые точки')

# Добавляем линию y = 0
plt.axhline(y=0, color='gray', linestyle='--', alpha=0.7)

# Добавляем линию x = 0
plt.axvline(x=0, color='gray', linestyle='--', alpha=0.7)

# Добавляем вертикальные асимптоты
plt.axvline(x=0, color='red', linestyle='--', alpha=0.3)
plt.axvline(x=np.pi, color='red', linestyle='--', alpha=0.3)
plt.axvline(x=2*np.pi, color='red', linestyle='--', alpha=0.3)

# Настройка графика
plt.title('График функции csc(x) с тестовыми точками')
plt.xlabel('x (радианы)')
plt.ylabel('csc(x)')
plt.grid(True, alpha=0.3)
plt.legend()

# Добавляем метки для особых точек на оси x
x_ticks = [0, np.pi/6, np.pi/4, np.pi/3, np.pi/2, 2*np.pi/3, 3*np.pi/4, 5*np.pi/6, np.pi, 
           7*np.pi/6, 5*np.pi/4, 4*np.pi/3, 3*np.pi/2, 5*np.pi/3, 7*np.pi/4, 11*np.pi/6, 2*np.pi]
x_tick_labels = ['0', 'π/6', 'π/4', 'π/3', 'π/2', '2π/3', '3π/4', '5π/6', 'π', 
                '7π/6', '5π/4', '4π/3', '3π/2', '5π/3', '7π/4', '11π/6', '2π']
plt.xticks(x_ticks, x_tick_labels, rotation=45)

# Устанавливаем пределы осей для лучшего отображения
plt.xlim(-0.5, 2*np.pi + 0.5)
plt.ylim(-5, 5)

# Показываем график
plt.tight_layout()
plt.show()
