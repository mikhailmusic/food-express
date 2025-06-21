# Food Express

**Food Express** — веб-приложение для онлайн-заказа еды, разработанное с использованием Spring Framework.

Особенностью проекта является **выделение отдельного интерфейсного модуля (контрактов)**, включающего контроллеры, формы и view-модели. Это позволяет:
- Чётко отделить слои взаимодействия с клиентской частью
- Упростить поддержку и масштабирование проекта
- Обеспечить повторное использование контрактов между разными реализациями фронтенда

## 📌 Описание

Сервис связывает клиентов с ресторанами, позволяя:
- Выбирать блюда по категориям
- Оформлять заказы из ресторана
- Оставлять отзывы и формировать рейтинг заведений
- Видеть самые популярные блюда

## 🛠 Стек технологий

- **Java**, **Spring Framework** (Web, Data, Security)
- **Thymeleaf** — шаблонизатор для HTML-страниц
- **PostgreSQL** — основная СУБД
- **Redis** — кэширование данных
- **Elastic Stack** (Elasticsearch, Logstash, Kibana) — логирование и мониторинг

## Демонстрация

<div>
  <img src="https://github.com/user-attachments/assets/f472a048-322f-456b-b4c7-7ca6f2150a57" width="750" />
  <p><em>Рис. 1. Главная страница приложения</em></p>
</div>

<div>
  <img src="https://github.com/user-attachments/assets/25b4b4ae-3c06-4e0a-a4de-3524002eb64c" width="750" />
  <p><em>Рис. 2. Страница ресторана</em></p>
</div>

<div>
  <img src="https://github.com/user-attachments/assets/eff7febd-0ff0-4583-92ff-637a107af80c" width="750" />
  <p><em>Рис. 3. Страница для создания заказа</em></p>
</div>

<div>
  <img src="https://github.com/user-attachments/assets/73c773dd-b331-4416-ae05-e0a045a54c3d" width="750" />
  <p><em>Рис. 4. История заказов пользователя</em></p>
</div>

<div>
  <img src="https://github.com/user-attachments/assets/0d87f4c9-873f-4a3d-938f-fd574174e6ca" width="750" />
  <p><em>Рис. 5. Популярные блюда</em></p>
</div>

<div>
  <img src="https://github.com/user-attachments/assets/c3f734a2-473a-4fa6-865b-5b367f3e1b22" width="750" />
  <p><em>Рис. 6. Добавление информации о ресторане администратором</em></p>
</div>

<div>
  <img src="https://github.com/user-attachments/assets/f5a953d0-be56-454e-afe3-734138b26cb6" width="600" />
  <p><em>Рис. 7. Поиск логов уровня WARN в Kibana</em></p>
</div>

