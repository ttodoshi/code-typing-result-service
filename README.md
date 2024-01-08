# Speed Typing result service on Java

speed typing results saving service written on java with MongoDB, Redis and Spring Boot

endpoints:

- **GET /api/v1/results/** (get saved results)
- **POST /api/v1/results/** (save result)

```json
{
  "textUUID": "0kldjga03458-sadgj-0-wejga",
  "symbolsPerSecond": [
    1,
    2,
    3,
    4,
    5
  ],
  "symbolsPerMinute": 300,
  "startTime": "2024-01-08T00:06:11.374197706+03:00",
  "endTime": "2024-01-08T00:06:50.374197706+03:00",
  "errorsCount": 6,
  "accuracy": 95.7
}
```
