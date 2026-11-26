# \# SmartBooking – App Móvil (Android)

# 

# Aplicación Android para la gestión de reservas del espacio de coworking de \*\*SmartBooking Solutions\*\*. Consume la API REST del backend (\[smartbooking-backend](https://github.com/smartbooking-ivo-konstantinow/smartbooking-backend)).

# 

# Proyecto del Final Libre del módulo \*\*Programador de Aplicaciones para Móviles\*\* (ISPC).

# 

# 📚 \*\*Documentación completa:\*\* \[Wiki del proyecto](https://github.com/smartbooking-ivo-konstantinow/smartbooking-app/wiki) · Diseño, Plan de Seguridad, Testing y Accesibilidad.

# 

# \## Tecnologías

# 

# \- Java · Android Studio · minSdk 21 (Android 5.0) · compileSdk 33

# \- Retrofit + Gson para el consumo de la API

# \- OkHttp Interceptor para enviar el token JWT

# \- EncryptedSharedPreferences (Android Keystore) para guardar los tokens

# \- JUnit para los tests unitarios

# 

# \## Requisitos previos

# 

# \- \*\*El backend funcionando\*\*: seguir primero el \[README del backend](https://github.com/smartbooking-ivo-konstantinow/smartbooking-backend#readme)

# \- \[Android Studio](https://developer.android.com/studio)

# \- Un emulador o un dispositivo físico con Android 5.0 o superior

# 

# > \*\*Importante (Windows):\*\* usar una terminal \*\*sin\*\* permisos de administrador.

# 

# \## Instalación paso a paso

# 

# \### 1. Clonar el repositorio

# 

# ```powershell

# git clone https://github.com/smartbooking-ivo-konstantinow/smartbooking-app.git

# ```

# 

# \### 2. Abrir el proyecto

# 

# En Android Studio: \*\*File → Open\*\* → elegir la carpeta `smartbooking-app`. Esperar a que termine la sincronización de Gradle (\*\*BUILD SUCCESSFUL\*\*).

# 

# \### 3. ⚠️ Configurar la URL del backend (obligatorio)

# 

# La URL del backend \*\*no está en el código\*\*: se lee del archivo `local.properties`, que está en la raíz del proyecto y no se sube al repositorio. Android Studio lo crea al abrir el proyecto. Agregar al final \*\*una\*\* de estas líneas, según cómo se ejecute la app:

# 

# \*\*Con el emulador:\*\*

# 

# ```properties

# BASE\_URL=http://10.0.2.2:8000/api/

# ```

# 

# `10.0.2.2` es la dirección con la que el emulador accede a la computadora.

# 

# \*\*Con un celular físico conectado por USB:\*\*

# 

# ```properties

# BASE\_URL=http://127.0.0.1:8000/api/

# ```

# 

# Y, con el celular conectado y la depuración USB activada, ejecutar:

# 

# ```powershell

# \& "$env:LOCALAPPDATA\\Android\\Sdk\\platform-tools\\adb.exe" reverse tcp:8000 tcp:8000

# ```

# 

# Este comando redirige el puerto 8000 del celular al de la computadora. Hay que repetirlo cada vez que se reconecta el celular.

# 

# La barra final de la URL es obligatoria. Después de editar `local.properties`: \*\*File → Sync Project with Gradle Files\*\*.

# 

# \### 4. Ejecutar la app

# 

# Con el backend corriendo, elegir el emulador o el dispositivo y presionar \*\*▶ Run\*\*.

# 

# \## Credenciales de demostración

# 

# Creadas en el backend con `python manage.py crear\\\\\\\_datos\\\\\\\_demo`. \*\*Son de demostración, no reales.\*\*

# 

# | Rol | Email | Contraseña |

# |---|---|---|

# | Administrador | admin@smartbooking.com | Admin#2026 |

# | Usuario | prueba@mail.com | Clave#123 |

# 

# \## Funcionalidades (Sprint 1)

# 

# \- \*\*Splash:\*\* si hay una sesión guardada, entra directo; si no, va al Login

# \- \*\*Registro\*\* con validación de contraseña robusta en la app y en el backend

# \- \*\*Login\*\* con JWT; los tokens se guardan cifrados

# \- \*\*Inicio\*\* con un menú distinto para el Administrador y el Usuario

# \- \*\*Cierre de sesión:\*\* anula el token en el servidor y lo borra del dispositivo

# 

# \## Estructura del proyecto

# 

# ```

# app/src/main/java/com/smartbooking/app/

# ├── ui/                  Pantallas (Activities)

# ├── data/

# │   ├── api/             Retrofit: ApiClient, ApiService, AuthInterceptor

# │   ├── model/           Objetos de solicitudes y respuestas

# │   └── repository/      TokenManager (almacenamiento cifrado)

# └── utils/               PasswordValidator

# ```

# 

# \## Endpoints consumidos

# 

# | Método | Endpoint | Uso en la app |

# |---|---|---|

# | POST | `/api/auth/register/` | Registro |

# | POST | `/api/auth/login/` | Inicio de sesión |

# | POST | `/api/auth/logout/` | Cierre de sesión |

# | GET | `/api/perfil/` | Nombre y rol para el menú de inicio |

# 

# \## Tests

# 

# Tests unitarios locales (no requieren dispositivo):

# 

# ```powershell

# .\\gradlew testDebugUnitTest

# ```

# 

# El reporte queda en `app/build/reports/tests/testDebugUnitTest/index.html`.

# 

# \## Solución de problemas

# 

# | Problema | Solución |

# |---|---|

# | La app se cierra al abrirse | Falta `BASE\\\\\\\_URL` en `local.properties` (paso 3) |

# | "No se pudo conectar con el servidor" | Verificar que el backend esté corriendo y, en celular físico, repetir el `adb reverse` |

# | El celular aparece como `unauthorized` | Revocar las autorizaciones de depuración USB en el celular, reconectar y aceptar el aviso |

# | Errores de versiones al sincronizar Gradle | Abrir el proyecto con Android Studio actualizado y sincronizar de nuevo |

# 

# \## Entregables

# 

# \- APK: \*pendiente (sección Releases)\*

# \- Videos de demostración: \*pendientes (enlazados en la Wiki)\*

