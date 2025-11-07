## Реализована новая функциональность — Comments (комментарии к событиям)

Теперь пользователи могут оставлять комментарии к событиям.  
Для реализации были написаны следующие классы и эндпоинты:

### Класс `Comment`:
```json
{
  "id": 1,
  "text": "Test",
  "event": {
    "id": 1,
    "name": "Test"
  },
  "owner": {
    "id": 1,
    "username": "Test",
    "email": "test@example.com"
  },
  "created": "2025-11-07T14:35:00"
}
```

### Класс `CommentDto`:
```json
{
  "id": 1,
  "text": "Test",
  "ownerName": "Test",
  "created": "2025-11-07T14:35:00"
}
```

### Класс `CreateCommentDto`:
```json
{
  "id": 1,
  "text": "Test"
}
```

### Эндпоинты

- **POST** `/{userId}/events/{eventId}/comments` — добавление нового комментария
- **PATCH** `/{userId}/events/{eventId}/comments` — изменение комментария
- **DELETE** `/{userId}/comments/{commentId}` — удаление комментария
- **GET** `/{eventId}/comments` — получение комментариев


