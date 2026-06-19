[![Qodana](https://github.com/fafeichter/ReparierenStattWegwerfen-BackOffice/actions/workflows/qodana_code_quality.yml/badge.svg)](https://github.com/fafeichter/ReparierenStattWegwerfen-BackOffice/actions/workflows/qodana_code_quality.yml)
[![Deployment](https://github.com/fafeichter/ReparierenStattWegwerfen-BackOffice/actions/workflows/deploy.yml/badge.svg)](https://github.com/fafeichter/ReparierenStattWegwerfen-BackOffice/actions/workflows/deploy.yml)

# Reparieren Statt Wegwerfen - Backoffice

Backoffice app of my company ["Reparieren Statt Wegwerfen"](https://reparieren-statt-wegwerfen.at/).

---

## 🏗️ Architecture & Design

The application is built adhering to modern architectural practices to ensure maintainability, scalability, and clean
boundaries:

- **Modular Monolith (Modulith):** Promotes a highly structured monolithic architecture with strictly enforced
  package boundaries, combining the simplicity of a single deployment with the decoupled nature of microservices.
- **Domain-Driven Design (DDD):** Core business logic is modeled after my real-world refurbishment and retail workflows.

The following domains have been identified:

| Domain               | Module Name       | Description                                                                                                                                                        |
|:---------------------|:------------------|:-------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Model**            | `model`           | Manages the catalog of supported hardware definitions (specifically Apple Silicon M1 or newer devices) targeted for repair and refurbishment.                      |
| **Business Partner** | `businesspartner` | Handles data of both suppliers (sellers) and clients (buyers).                                                                                                     |
| **Device**           | `device`          | Tracks the entire lifecycle of a specific, physical asset—from initial acquisition through repair stages to a successful sale, or its disassembly for spare parts. |
| **Sale**             | `sale`            | Aggregates data from the Model, Business Partner, and Device domains to generate invoices.                                                                         |

---

## 🛠️ Tech Stack

- **Backend:** Java / Spring Boot
- **Frontend:** Angular + Clarity Design System
- **Database:** MySQL
- **Identity & Access Management:** Keycloak

---

## 📄 License

This project is licensed under the terms of the open-source license included in the repository. See the `LICENSE` file
for details.