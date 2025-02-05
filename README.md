🚀 Modern Multi-tenant SaaS Starter Kit
A production-ready starter kit for building scalable SaaS applications with Spring Boot and React. This boilerplate provides a solid foundation with built-in multi-tenancy support, modern frontend architecture, and enterprise-grade security features.


✨ Key Features

🏢 Built-in Multi-tenancy: Secure data isolation between tenants
🔒 Enterprise Security: JWT authentication, role-based access control
⚡ Modern Tech Stack: Spring Boot 3.x + React 18 with Vite
🎨 Polished UI: Clean design with Tailwind CSS
🔍 Type Safety: Full TypeScript support on the frontend

System Requirements
To run this application successfully, your system should meet the following requirements:

Java: JDK 23 or higher
Node.js: v16.x or higher (v18+ recommended)
PostgreSQL: Version 13 or higher
Maven: Version 3.6 or higher

🛠️ Technology Stack
Backend Technologies
Our backend is built using Spring Boot 3.4.0 and provides a robust foundation with the following key components:
Core Dependencies:

Spring Boot Starter Web: Foundation for building web applications
Spring Boot Data JPA: Simplified data access layer with Hibernate
Spring Security: Advanced authentication and authorization
OAuth2 Client: Support for OAuth2 authentication flows
PostgreSQL: Production-grade relational database
Lombok: Reduces boilerplate code through annotations
JWT (jsonwebtoken): Secure token-based authentication with JJWT 0.11.5

Development Tools:

Spring Boot DevTools: Enhanced development experience
Java Version: JDK 23 compatibility
Spring Boot Test: Comprehensive testing framework

Frontend Technologies
Our frontend utilizes modern React with TypeScript and Vite, incorporating these carefully selected dependencies:
Core Framework:

React: v18.3.1 with React DOM
TypeScript: v5.6.2 for type safety
Vite: v5.4.10 for lightning-fast builds

UI & Styling:

Tailwind CSS: v3.4.15 for utility-first styling
Lucide React: v0.461.0 for beautiful icons
React Icons: v5.3.0 for additional icon options
Class Variance Authority: For reusable component styles
Tailwind Merge & Animate: Enhanced styling capabilities

Routing & HTTP:

React Router DOM: v7.0.1 for client-side routing
Axios: v1.7.7 for HTTP requests

Development Tools:

ESLint: v9.13.0 with React plugins
TypeScript ESLint: v8.11.0
Autoprefixer: v10.4.20
PostCSS: v8.4.49

🏗️ Project Setup Options
This starter kit offers two flexible setup options to match your development needs:
Monolithic Setup (Default)
The project comes with a monolithic structure where both frontend and backend coexist in the same repository, making it easier to manage during initial development:

```text
  your-project/
├── src/
│   ├── main/
│   │   ├── java/           # Backend code
│   │   └── resources/      # Backend resources
│   └── frontend/          # Frontend React application
```

Split Setup
You can also split the frontend and backend into separate repositories while maintaining the same configuration:

```text
  backend-project/
└── src/
    └── main/
        ├── java/
        └── resources/

frontend-project/
└── src/
    └── frontend/
```


🚀 Getting Started

Prerequisites
Before you begin, ensure you have installed:

JDK 17
Node.js (Latest LTS version)
PostgreSQL
Maven

Quick Start

1 / Clone the repository
```bash
git clone https://github.com/yourusername/saas-starter-kit
cd saas-starter-kit
```

2 / Install Dependencies

For the backend:
```bash
mvn clean install
```

For the frontend:
```bash
npm install
```

3 / Configure the Database

    - Create a new PostgreSQL database
    - Update the database configuration in application.properties
    
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/your_db
spring.datasource.username=your_username
spring.datasource.password=your_password
```

4 / Run the Application
    
For the backend:
```bash
cd backend
mvn spring-boot:run
```

For the frontend:
```bash
cd frontend
npm install
npm run dev
```


Your application will be available at:

Backend API: http://localhost:8080
Frontend: http://localhost:5173


📁 Project Structure
    
Frontend Structure 
```text
├── public/                 # Public assets
│   └── vite.svg           # Vite logo
├── src/
│   ├── api/               # API integration layer
│   ├── assets/            # Static assets like images
│   ├── components/        # Reusable React components
│   ├── context/           # React Context providers
│   ├── lib/               # Utility libraries
│   ├── pages/             # Page components
│   ├── App.css            # Main application styles
│   ├── App.tsx            # Root React component
│   ├── index.css          # Global styles
│   ├── main.tsx          # Application entry point
│   └── vite-env.d.ts     # Vite environment types
├── .env                   # Environment variables
├── .gitignore            # Git ignore rules
├── components.json        # UI components configuration
├── eslint.config.js      # ESLint configuration
├── index.html            # HTML entry point
├── package.json          # Node.js dependencies and scripts
├── package-lock.json     # Locked dependencies
```


Backend Structure
```text
src/
├── main/
│   ├── java/
│   │   └── com.yourpackage.springreact/
│   │       ├── config/            # Spring configuration classes
│   │       ├── controller/        # REST API controllers
│   │       ├── dto/               # Data transfer objects
│   │       ├── filter/            # Security and tenant filters
│   │       ├── model/             # Domain entities
│   │       ├── repository/        # Data access layer
│   │       ├── service/           # Business logic services
│   │       ├── tenant/            # Multi-tenancy implementation
│   │       ├── util/              # Utility classes
│   │       └── SpringReactApplication.java  # Main application class
│   └── resources/
│       ├── static/                # Static resources
│       ├── templates/             # Template files
│       └── application.properties # Application configuration
├── test/                         # Test directory
├── target/                       # Compiled output
├── .gitattributes               # Git attributes
├── .gitignore                   # Git ignore rules
├── HELP.md                      # Spring Boot help
├── mvnw                         # Maven wrapper script
├── mvnw.cmd                     # Maven wrapper for Windows
├── pom.xml                      # Maven dependencies
└── README.md                    # Backend documentation
```

🤝 Contributing
We welcome contributions!

Step 1: Fork the Project
Click the "Fork" button at the top right of this repository to create your own copy.

Step 2: Create Your Feature Branch
```bash
git checkout -b feature/AmazingFeature
```

Step 3: Submit Your Changes
Create a Pull Request from your feature branch to our master branch.
That's it! We'll review your contribution and get back to you. Thank you for helping make this project better! 🚀




🚧 Features in Development
We are actively working on expanding the capabilities of this starter kit. Here are some exciting features currently in development:
Payment Integration

💳 Stripe payment integration
Subscription management
Usage-based billing
Invoice generation
Payment webhooks



📁 Amazon S3 integration
File upload/download management
Secure file access control
Multi-tenant file isolation
Automatic cleanup policies

These features are being developed with the same attention to security, scalability, and multi-tenant isolation that characterizes our current codebase. Stay tuned for updates!
📝 License
This project is licensed under the MIT License - see the LICENSE file for details.
🙋 Support
If you have any questions or need help, please:

Contact me by email

⭐ Show your support
Give a ⭐️ if this project helped you!

Made with ❤️ by [Nilsw13]