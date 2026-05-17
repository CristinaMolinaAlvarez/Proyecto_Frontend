console.log("AVAILABILITY USER JS CARGADO");

const API_URL = "http://localhost:8080";

document.addEventListener("DOMContentLoaded", function () {
  const email    = localStorage.getItem("usuarioEmail");
  const password = localStorage.getItem("usuarioPassword");

  if (!email || !password) {
    location.href = "login.html";
    return;
  }

  const form = document.getElementById("form-disponibilidad");
  form.addEventListener("submit", function (event) {
    event.preventDefault();
    cargarDisponibilidad();
  });
});

function cargarDisponibilidad() {
  const email    = localStorage.getItem("usuarioEmail");
  const password = localStorage.getItem("usuarioPassword");
  const fecha    = document.getElementById("date").value;
  const courtId  = document.getElementById("courtId").value;

  let url = `${API_URL}/pistaPadel/availability?date=${fecha}`;
  if (courtId !== "") {
    url += `&courtId=${courtId}`;
  }

  console.log("URL disponibilidad:", url);

  fetch(url, {
    method: "GET",
    headers: {
      "Authorization": "Basic " + btoa(email + ":" + password)
    }
  })
  .then(response => {
    console.log("Status disponibilidad:", response.status);

    if (response.status === 401) {
      location.href = "login.html";
      return null;
    }
    if (!response.ok) {
      mostrarAviso("✖︎ Error al cargar disponibilidad: " + response.status, "error");
      return null;
    }
    return response.json();
  })
  .then(datos => {
    if (!datos) return;
    const disponibilidad = Array.isArray(datos) ? datos : [datos];
    console.log("Disponibilidad recibida:", disponibilidad);
    pintarDisponibilidad(disponibilidad);
  })
  .catch(error => {
    console.error(error);
    mostrarAviso("✖︎ No se pudo conectar con el backend", "error");
  });
}

function pintarDisponibilidad(disponibilidad) {
  const contenedor = document.getElementById("lista-disponibilidad");

  if (!disponibilidad || disponibilidad.length === 0) {
    contenedor.innerHTML = "<p>No hay pistas disponibles.</p>";
    return;
  }

  let html = "";

  disponibilidad.forEach(pista => {
    const slots = pista.franjasDisponibles || [];

    html += `
      <article class="tarjeta tarjeta-disponibilidad">
        <div class="cabecera-pista">
          <div>
            <h3>Pista ${pista.idPista}</h3>
            <p>Fecha: ${pista.fecha || ""}</p>
          </div>
          <a href="reservation.html" class="boton boton-principal">Reservar esta pista</a>
        </div>
        <div class="slots">
          ${pintarHoras(slots)}
        </div>
      </article>
    `;
  });

  contenedor.innerHTML = html;
}

function pintarHoras(slots) {
  if (!slots || slots.length === 0) {
    return "<p>No hay horas disponibles.</p>";
  }
  return slots.map(hora => `<span>${hora}</span>`).join("");
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
  aviso.textContent      = texto;
  aviso.style.color      = tipo === "error" ? "#c0392b" : "#27ae60";
  aviso.style.fontWeight = "bold";
}
