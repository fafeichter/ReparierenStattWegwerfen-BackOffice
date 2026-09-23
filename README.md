[![Qodana](https://github.com/fafeichter/ReparierenStattWegwerfen-BackOffice/actions/workflows/qodana_code_quality.yml/badge.svg)](
https://github.com/fafeichter/ReparierenStattWegwerfen-BackOffice/actions/workflows/qodana_code_quality.yml)
[![Deployment](https://github.com/fafeichter/ReparierenStattWegwerfen-BackOffice/actions/workflows/deploy.yml/badge.svg)](
https://github.com/fafeichter/ReparierenStattWegwerfen-BackOffice/actions/workflows/deploy.yml)

# Reparieren Statt Wegwerfen - Backoffice

This is the back-office application for my company,
[Reparieren Statt Wegwerfen](https://reparieren-statt-wegwerfen.at/), used to manage and track
my repair and refurbishment workflow. It replaces my legacy Apple Numbers-based solution,
which had reached the limits of its capabilities.

I decided to open-source this project to enforce better security practices, such as keeping
secrets out of the repository.

---

## Development Approach

Since I need this fast, I follow this approach: _Make it exist first, make it better later._

---

## 🏗️ Architecture & Design

The application is built adhering to modern architectural practices to ensure maintainability,
scalability, and clean boundaries:

- **Modular Monolith (Modulith):** Promotes a highly structured monolithic architecture with
  strictly enforced package boundaries, combining the simplicity of a single deployment with the
  decoupled nature of microservices.
- **Domain-Driven Design (DDD):** Core business logic is modeled after my real-world
  refurbishment and repair workflows.

The following domains have been identified:

| Domain               | Module Name       | Description                                                                                                                                                                  |
|:---------------------|:------------------|:-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Device**           | `device`          | Tracks the entire lifecycle of a specific MacBook or iPad within the company — from initial acquisition through repair stages to resale, or its disassembly for spare parts. |
| **Business Partner** | `businesspartner` | A person or a company I buy from or sell devices and/or spare parts to..                                                                                                     |
| **Model**            | `model`           | Defines all models I repair or refurbish: MacBooks and iPads with Apple Silicon.                                                                                             |
| **Spare Part**       | `sparepart`       | Manages spare parts required for device repair and refurbishment, sourced from business partners or harvested from disassembled devices.                                     |
| **Accounting**       | `accounting`      | Keeps track of balances, taxes, and invoices. (To be implemented as part of milestone 2).                                                                                    |

![Application Modules](./backend/docs/spring-modulith/modules.svg)

---

## 📌 Development Milestones

- **Milestone 1 (Target: Q4 2026):** Replace the existing Apple Numbers spreadsheet with a modern web application based on Domain-Driven Design (DDD) principles.
- **Milestone 2 (Target: 2027)**: Integrate the Accounting domain.

_Every milestone includes maintaining code health and stability, as well as strictly adhering to DDD principles._

## 🛠️ Tech Stack

- **Backend:** Java / Spring Boot
- **Frontend:** Angular + Clarity Design System
- **Database:** MySQL
- **Identity & Access Management:** Keycloak

---

## 👨‍💻️ Development Setup

Executing `generateDoc` to render the project's module diagrams requires Graphviz. You can install the dependency via Homebrew:

```bash
brew install graphviz
```

---

## 📄 License

This project is licensed under the terms of the open-source license included in the repository.
See the `LICENSE` file for details.