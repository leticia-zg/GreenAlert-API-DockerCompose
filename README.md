# 🌱 GreenAlert API — Docker Compose

Este README documenta a solução do **Checkpoint - Docker Compose**.  
Projeto containerizado com **Spring Boot API**, **MySQL** e **Adminer**, cumprindo todos os requisitos do enunciado.

---

## 🏗️ Arquitetura

### Antes (sem containers)
```mermaid
flowchart LR
  Dev[Dev Machine] --> AppLocal[API Java (local)]
  AppLocal --> DBLocal[(MySQL local)]
```

### Depois (com Docker Compose)
```mermaid
flowchart LR
  User((Cliente)) --> API[API (app container)]
  API --> DB[(MySQL container)]
  Adminer[Adminer container] --> DB
  subgraph backend [Docker network]
    API
    DB
    Adminer
  end
```

### 🔎 Análise da Arquitetura
- **Serviços:**  
  - `app` → API Spring Boot (build via Dockerfile)  
  - `db` → MySQL (imagem oficial)  
  - `adminer` → Adminer (imagem oficial)  
- **Dependências:** `app` depende do `db`; `adminer` também depende do `db`. Ordem garantida via `depends_on` + healthchecks.  
- **Estratégia de containerização:** app é construído em multi-stage Dockerfile (Maven + Temurin JRE, roda como usuário não-root). Banco/Adminer usam imagens oficiais.  
- **Rede & portas:** rede bridge `backend`, expõe `8080` (API) e `8081` (Adminer). MySQL só interno (`3306`).  
- **Persistência:** volume `mysql-data` para `/var/lib/mysql`.  

---

## 🚀 Como rodar

### Passos principais
```bash
# (opcional) limpar estado anterior
docker compose down -v

# subir (build + iniciar containers)
docker compose up -d --build

# ver status e logs
docker compose ps
docker compose logs -f app   # Ctrl+C para sair
```

### Acessos rápidos
- Swagger → [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)  
- Adminer → [http://localhost:8081](http://localhost:8081)  

### Derrubar
```bash
docker compose down -v
```

---

## ⚙️ Serviços e portas

| Serviço   | Origem            | Porta host | Porta container |
|-----------|-------------------|------------|-----------------|
| **app**   | Build via Dockerfile | 8080       | 8080            |
| **db**    | mysql:8.0 (oficial) | —          | 3306            |
| **adminer** | adminer:latest (oficial) | 8081       | 8080            |

- Rede: `backend`  
- Persistência: `mysql-data`  

---

## 🔑 Variáveis (.env)

```env
APP_PORT=8080
MYSQL_DATABASE=monitor_tree
MYSQL_USER=app
MYSQL_PASSWORD=app
MYSQL_ROOT_PASSWORD=changeit
```
A API conecta usando `jdbc:mysql://db:3306/${MYSQL_DATABASE}`.

---

## 🩺 Healthchecks

- **db** → `mysqladmin ping -h localhost -p$MYSQL_ROOT_PASSWORD`  
- **app** → `curl -fsS http://app:8080/swagger-ui/index.html || exit 1`  
> Garantem que o Compose só considera os serviços “saudáveis” quando estão prontos.

---

## 👤 Usuário não-root

O `Dockerfile` da API cria o usuário `spring` e executa o container com `USER spring`.

---

## 🧪 Demonstração rápida (JWT + CRUD)

### 1) Login (ADMIN)
```bash
curl -s -X POST http://localhost:8080/login   -H "Content-Type: application/json"   -d '{"email":"celina@fiap.com.br","password":"12345"}'
```

### 2) CRUD de `Sensor`
```bash
# CREATE
curl -X POST http://localhost:8080/sensores   -H "Authorization: Bearer $TOKEN" -H "Content-Type: application/json"   -d '{"nome":"Sensor A","tipo":"TEMPERATURA","localizacao":"-23.55,-46.63"}'

# READ (lista)
curl -H "Authorization: Bearer $TOKEN" http://localhost:8080/sensores

# UPDATE
curl -X PUT http://localhost:8080/sensores/ID   -H "Authorization: Bearer $TOKEN" -H "Content-Type: application/json"   -d '{"nome":"Sensor A v2","tipo":"TEMPERATURA","localizacao":"-23.56,-46.62"}'

# DELETE
curl -X DELETE http://localhost:8080/sensores/ID -H "Authorization: Bearer $TOKEN"
```

### 3) Comprovação no banco (Adminer)
- System: **MySQL**  
- Server: `db`  
- Username: `app`  
- Password: `app`  
- Database: `monitor_tree`  
- Tabelas: `sensor`, `leitura` (mostrar INSERT/UPDATE/DELETE).  

---

## 🛠️ Troubleshooting

- **`version` obsoleta** → remova do `docker-compose.yml`.  
- **Imagem não encontrada** → mantenha só `build:` no serviço `app` ou rode `docker compose build`.  
- **Porta ocupada** → altere `APP_PORT` no `.env` ou libere.  
- **401/403** → refaça login e configure `Bearer <token>`.  
