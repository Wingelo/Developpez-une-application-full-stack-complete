# P6-Full-Stack-reseau-dev

## Back

### Maven Commands to Run the Project
1. **Compile and Build the Project:**
```bash
mvn clean install
```

### Modify the application.properties file accordingly for the application to start (You will find numbers 1 to 2 that you need to modify)
- 1# Database Configuration
- 2# Application Configuration
- For the secret key, you must run the command in bash (for example: GIT BASH)
```bash
openssl rand -base64 32
```

## Front

This project was generated with [Angular CLI](https://github.com/angular/angular-cli) version 14.1.3.

Don't forget to install your node_modules before starting (`npm install`).

### Development server

Run `ng serve` for a dev server. Navigate to `http://localhost:4200/`. The application will automatically reload if you change any of the source files.

### Build

Run `ng build` to build the project. The build artifacts will be stored in the `dist/` directory.

### Where to start

As you may have seen if you already started the app, a simple home page containing a logo, a title and a button is available. If you take a look at its code (in the `home.component.html`) you will see that an external UI library is already configured in the project.

This library is `@angular/material`, it's one of the most famous in the angular ecosystem. As you can see on their docs (https://material.angular.io/), it contains a lot of highly customizable components that will help you design your interfaces quickly.

Note: I recommend to use material however it's not mandatory, if you prefer you can get rid of it.

Good luck!
