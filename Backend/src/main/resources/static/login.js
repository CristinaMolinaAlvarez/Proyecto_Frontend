console.log("LOGIN JS CARGADO");

const API_URL = "http://localhost:8080";

document.addEventListener("DOMContentLoaded", function () {
  console.log("DOM LOGIN CARGADO");
  inicializar();

  const form = document.getElementById("form-login");
  form.addEventListener("submit", function (event) {
    event.preventDefault();
    entrar();
  });
});

function inicializar() {
  if (location.search === '?registrado') {
    mostrarAviso('✓ ¡Registrado! Prueba a entrar', 'success');
  }
}

async function entrar() {
  const email    = document.getElementById("email").value;
  const password = document.getElementById("password").value;

  console.log("Intentando login con email:", email);

  try {
    // Spring Security formLogin espera application/x-www-form-urlencoded
    // con los campos "username" y "password" (no JSON)
    const body = new URLSearchParams();
    body.append("username", email);
    body.append("password", password);

    const response = await fetch(`${API_URL}/pistaPadel/auth/login`, {
      method: "POST",
      headers: { "Content-Type": "application/x-www-form-urlencoded" },
      body: body.toString(),
      credentials: "include"
    });

    console.log("Status login:", response.status);

    if (response.ok) {
      // Guardamos credenciales para Basic Auth en el resto de peticiones
      localStorage.setItem("usuarioEmail", email);
      localStorage.setItem("usuarioPassword", password);

      // Obtenemos datos del usuario para saber el rol
      const meResponse = await fetch(`${API_URL}/pistaPadel/auth/me`, {
        headers: { "Authorization": "Basic " + btoa(email + ":" + password) }
      });

      if (meResponse.ok) {
        const usuario = await meResponse.json();
        localStorage.setItem("usuarioId",  usuario.idUsuario);
        localStorage.setItem("usuarioRol", usuario.rol);
        console.log("Usuario autenticado:", usuario.email, "| Rol:", usuario.rol);

        // Redirigir según rol
        if (usuario.rol === "ADMIN") {
          location.href = "admin-home.html";
        } else {
          location.href = "user-home.html";
        }
      } else {
        mostrarAviso("✖︎ No se pudieron obtener los datos del usuario", "error");
      }

    } else {
      mostrarAviso("✖︎ Credenciales incorrectas", "error");
    }

  } catch (error) {
    console.error(error);
    mostrarAviso("✖︎ No se pudo conectar con el backend", "error");
  }
}

function mostrarAviso(texto, tipo) {
  const aviso = document.getElementById("aviso");
  aviso.textContent  = texto;
  aviso.style.color  = tipo === "error" ? "#c0392b" : "#27ae60";
  aviso.style.fontWeight = "bold";
}
