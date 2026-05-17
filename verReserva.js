console.log("MIS RESERVAS JS CARGADO");

const API_URL = "http://localhost:8080";

document.addEventListener("DOMContentLoaded", function () {
  const email    = localStorage.getItem("usuarioEmail");
  const password = localStorage.getItem("usuarioPassword");
  if (!email || !password) {
    location.href = "login.html";
    return;
  }
  cargarReservas();
});

function cargarReservas() {
  const email    = localStorage.getItem("usuarioEmail");
  const password = localStorage.getItem("usuarioPassword");

  if (!email || !password) {
    location.href = "login.html";
    return;
  }

  fetch(`${API_URL}/pistaPadel/reservations`, {
    method: "GET",
    headers: {
      // Basic Auth necesario: el endpoint requiere autenticación
      "Authorization": "Basic " + btoa(email + ":" + password)
    }
  })
  .then(response => {
    console.log("Status reservas:", response.status);

    if (response.status === 401) {
      mostrarAviso("✖︎ Sesión expirada. Vuelve a iniciar sesión.", "error");
      location.href = "login.html";
      return [];
    }

    if (!response.ok) {
      mostrarAviso("✖︎ Error al cargar reservas: " + response.status, "error");
      return [];
    }

    return response.json();
  })
  .then(reservas => {
    if (reservas) pintarReservas(reservas);
  })
  .catch(error => {
    console.error(error);
    mostrarAviso("✖︎ No se pudo conectar con el backend", "error");
  });
}

function pintarReservas(reservas) {
  const contenedor = document.getElementById("lista-reservas");

  if (reservas.length === 0) {
    contenedor.innerHTML = "<p>No tienes reservas todavía.</p>";
    return;
  }

  let html = "";

  reservas.forEach(reserva => {
    const esActiva   = reserva.estado === "ACTIVA";
    const claseCard  = esActiva ? "tarjeta tarjeta-reserva" : "tarjeta tarjeta-reserva tarjeta-reserva-cancelada";
    const claseEstado = esActiva ? "estado estado-activa" : "estado estado-cancelada";
    const textoEstado = esActiva ? "Activa" : "Cancelada";

    const botonesAccion = esActiva ? `
      <div class="grupo-botones reserva-acciones">
        <a href="reservation.html?edit=${reserva.idReserva}" class="boton boton-secundario">Modificar</a>
        <button type="button" class="boton boton-cancelar"
          onclick="cancelarReserva(${reserva.idReserva}, this)">Cancelar</button>
      </div>` : '';

    html += `
      <article class="${claseCard}">
        <div class="reserva-top">
          <h3>Reserva #${reserva.idReserva}</h3>
          <span class="${claseEstado}">${textoEstado}</span>
        </div>
        <div class="detalle-reserva">
          <p><strong>Pista:</strong> ${reserva.pista?.nombre || "Sin pista"}</p>
          <p><strong>Fecha:</strong> ${reserva.fechaReserva}</p>
          <p><strong>Hora inicio:</strong> ${reserva.horaInicio}</p>
          <p><strong>Duración:</strong> ${reserva.duracionMinutos} minutos</p>
        </div>
        ${botonesAccion}
      </article>
    `;
  });

  contenedor.innerHTML = html;
}

function cancelarReserva(idReserva, boton) {
  if (!confirm("¿Seguro que quieres cancelar esta reserva?")) return;

  const email    = localStorage.getItem("usuarioEmail");
  const password = localStorage.getItem("usuarioPassword");

  fetch(`${API_URL}/pistaPadel/reservations/${idReserva}`, {
    method: "DELETE",
    headers: {
      "Authorization": "Basic " + btoa(email + ":" + password)
    }
  })
  .then(response => {
    if (response.ok || response.status === 204) {
      // Recargar las reservas para mostrar el estado actualizado
      cargarReservas();
    } else {
      mostrarAviso("✖︎ No se pudo cancelar la reserva: " + response.status, "error");
    }
  })
  .catch(error => {
    console.error(error);
    mostrarAviso("✖︎ No se pudo conectar con el backend", "error");
  });
}

function logout() {
  localStorage.removeItem("usuarioId");
  localStorage.removeItem("usuarioEmail");
  localStorage.removeItem("usuarioPassword");
  localStorage.removeItem("usuarioRol");
  location.href = "login.html";
}

function mostrarAviso(texto, tipo) {
  const aviso = document.getElementById("aviso");
  aviso.textContent = texto;
  aviso.style.color  = tipo === "error" ? "#c0392b" : "#27ae60";
  aviso.style.fontWeight = "bold";
}
