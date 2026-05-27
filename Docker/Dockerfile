FROM eclipse-temurin:17-jdk-jammy

# Configuración de la aplicación
ENV APP_HOME=/api-orders-app
RUN mkdir -p $APP_HOME
WORKDIR $APP_HOME

# Puerto a exponer (Tu app de órdenes usa el puerto 8080 por defecto)
EXPOSE 8080

# Copiar el JAR (Se adapta al nombre generado por tu empaquetado de api-orders)
COPY target/api-orders-*.jar app.jar

# Usuario no-root para seguridad (Corregido el operador &&)
RUN useradd -m appuser && chown -R appuser:appuser $APP_HOME
USER appuser

# Salud del sistema interno (Apuntando al puerto 8080)
HEALTHCHECK --interval=30s --timeout=3s \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

# Comando de ejecución
ENTRYPOINT ["java", "-jar", "app.jar"]