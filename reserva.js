console.log("RESERVATION JS CARGADO");

const API_URL = "http://localhost:8080";

document.addEventListener("DOMContentLoaded", function () {
  const email    = localStorage.getItem("usuarioEmail");
  const password = localStorage.getItem("usuarioPassword");

  if (!email || !password) {
    location.href = "login.html";
    return;
  }

  const form = document.getElementById("form-reserva");

  form.addEventListener("submit", function (event) {
    event.preventDefault();
    const datosJsonFormulario = form2json(event);
    reservar(datosJsonFormulario);
  });

  function reservar(datosJsonFormulario) {
    console.log("RESERVA:", datosJsonFormulario);
    console.log("EMAIL AUTH:", email);

    fetch(`${API_URL}/pistaPadel/reservations`, {
      method: "POST",
      body: datosJsonFormulario,
      headers: {
        "Content-Type": "application/json",
        "Authorization": "Basic " + btoa(email + ":" + password)
      }
    })
    .then(response => {
      console.log("Status reserva:", response.status);
      if (response.status === 401) {
        location.href = "login.html";
        return;
      }
      if (response.ok) {
        location.href = "my-reservations.html";
      } else {
        mostrarAviso("✖︎ Error en la reserva: " + response.status, "error");
      }
    })
    .catch(error => {
      console.error(error);
      mostrarAviso("✖︎ No se pudo conectar con el backend", "error");
    });
  }

  function mostrarAviso(texto, tipo) {
    const aviso = document.getElementById("aviso");
    aviso.textContent = texto;
    aviso.style.color = tipo === "error" ? "red" : "green";
    aviso.className = tipo;
  }

  function form2json(event) {
    event.preventDefault();
    const data = new FormData(event.target);
    return JSON.stringify(Object.fromEntries(data.entries()));
  }
});

function logout() {
  localStorage.removeItem("usuarioId");
  localStorage.removeItem("usuarioEmail");
  localStorage.removeItem("usuarioPassword");
  localStorage.removeItem("usuarioRol");
  location.href = "login.html";
}
