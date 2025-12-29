# Prueba Técnica Backend – Microservicios de Productos e Inventario

## 📌 Descripción General

En este proyecto implementa una solución basada en **dos microservicios independientes**: **Product Service** y **Inventory Service**, los cuales interactúan entre sí mediante **HTTP** siguiendo el estándar **JSON:API**.

La solución cumple con los requerimientos de la prueba técnica, incluyendo:

* Arquitectura de microservicios
* Comunicación entre servicios
* Proceso de compra con validación de inventario
* Dockerización y orquestación con Docker Compose
* Pruebas unitarias e integración
* Documentación mediante colección de Postman

---

## 🧱 Arquitectura

### Microservicios

#### 1️⃣ Product Service

Responsable de la gestión de productos.

**Modelo:**

* `id`
* `nombre`
* `precio`
* `descripcion` (opcional)

**Funcionalidades:**

* Crear producto
* Obtener producto por ID

#### 2️⃣ Inventory Service

Responsable de la gestión del inventario y del proceso de compra.

**Modelo:**

* `productId`
* `cantidad`

**Funcionalidades:**

* Crear / actualizar inventario
* Consultar inventario por producto
* Proceso de compra

📌 **Decisión clave:**
El endpoint de **compra** fue implementado en el **Inventory Service**, ya que este microservicio es el responsable directo de la consistencia del stock. El servicio de productos se consume únicamente para obtener información del producto (precio y nombre), evitando duplicación de responsabilidades y manteniendo bajo acoplamiento.

---

## 🔄 Flujo de Compra

1. El cliente envía el `productId` y la `cantidad` a comprar.
2. Inventory Service:

   * Consulta el producto en Product Service.
   * Valida existencia del inventario.
   * Verifica stock suficiente.
   * Actualiza la cantidad disponible.
3. Retorna la información de la compra:

   * Producto
   * Precio unitario
   * Cantidad comprada
   * Total
   * Stock restante

Manejo de errores:

* Producto no existe → `404`
* Inventario no existe → `404`
* Stock insuficiente → `400`

---

## 🔐 Seguridad

* Autenticación **Basic Auth** en ambos servicios.
* Credenciales configurables mediante variables de entorno.

---

## 🗄️ Base de Datos

Se utilizó **SQLite** por las siguientes razones:

* Ligera y fácil de configurar
* Ideal para pruebas técnicas
* Persistencia simple sin dependencias externas

Cada microservicio posee su propia base de datos, garantizando independencia.

---

## 🐳 Docker

### Requisitos

* Docker
* Docker Compose

### Construcción y ejecución

Desde la raíz del proyecto:

```bash
docker compose up --build
```

Servicios expuestos:

* Product Service: `http://localhost:8081`
* Inventory Service: `http://localhost:8082`

---

## 🧪 Testing

### Pruebas implementadas

* Pruebas unitarias:

  * Creación de productos
  * Gestión de inventario
  * Proceso de compra
  * Manejo de errores

* Pruebas de integración:

  * Al menos una prueba por microservicio

Las pruebas validan tanto la lógica de negocio como la comunicación entre servicios.

---

## 📬 Documentación de API

Debido a incompatibilidades de versiones entre **Spring Boot 3.x** y `springdoc-openapi`, se optó por documentar la API mediante **Postman**.

### Postman

* Archivo incluido: `inventory-product.postman_collection.json`
* Environment: `local.postman_environment.json`

Variables del environment:

* `product_base_url` → `http://localhost:8081`
* `inventory_base_url` → `http://localhost:8082`
* `basic_auth_user`
* `basic_auth_password`

---

## 🤖 Uso de IA en el Desarrollo

Se utilizó **IA (ChatGPT)** como apoyo para:

* Diseño de arquitectura
* Resolución de errores de configuración
* Optimización de código
* Validación de buenas prácticas

Todo el código generado fue revisado, ajustado y validado manualmente mediante pruebas unitarias, integración y pruebas en Postman.

---

## 📈 Buenas Prácticas

* Separación de responsabilidades
* DTOs para comunicación
* Manejo centralizado de errores
* Uso de Lombok
* Git Flow durante el desarrollo

---

## 🚀 Ejecución Rápida

1. Clonar el repositorio
2. Ejecutar:

   ```bash
   docker compose up --build
   ```
3. Importar colección de Postman
4. Configurar environment
5. Probar endpoints

---

## 📌 Notas Finales

Este proyecto prioriza la **calidad del código**, claridad arquitectónica y cumplimiento de los requisitos solicitados, demostrando una solución robusta, escalable y alineada con buenas prácticas de desarrollo backend.
