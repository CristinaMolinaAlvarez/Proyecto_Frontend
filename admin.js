console.log("ADMIN JS CARGADO");

const API_URL = "http://localhost:8080";

//comprueba que hay sesión activa con rol ADMIN
// Si no, redirige al login. Llamamos al inicio de cada página admin.
function verificarAdmin() {
  const email    = localStorage.getItem("usuarioEmail");
  const password = localStorage.getItem("usuarioPassword");
  const rol      = localStorage.getItem("usuarioRol");

  if (!email || !password) {
    location.href = "login.html";
    return false;
  }

  if (rol !== "ADMIN") {
    // Es usuario normal, lo mandamos a su página
    location.href = "user-home.html";
    return false;
  }

  return true;
}

//Cabecera de autorización para todas las peticiones
function authHeader() {
  const email    = localStorage.getItem("usuarioEmail");
  const password = localStorage.getItem("usuarioPassword");
  return { "Authorization": "Basic " + btoa(email + ":" + password) };
}

//Logout: limpia localStorage y vuelve al login
function logout() {
  localStorage.removeItem("usuarioId");
  localStorage.removeItem("usuarioEmail");
  localStorage.removeItem("usuarioPassword");
  localStorage.removeItem("usuarioRol");
  location.href = "login.html";
}


// ADMIN-COURTS: carga pistas del backend y realiza la gestión básica
function iniciarAdminCourts() {
  if (!verificarAdmin()) return;
  cargarPistas();

  const btnAniadir = document.getElementById("btn-aniadir-pista");
  if (btnAniadir) {
    btnAniadir.addEventListener("click", mostrarFormularioPista);
  }
}

function cargarPistas() {
  fetch(`${API_URL}/pistaPadel/courts`, {
    headers: authHeader()
  })
  .then(r => {
    if (!r.ok) throw new Error("Error " + r.status);
    return r.json();
  })
  .then(pistas => pintarTablaPistas(pistas))
  .catch(err => {
    console.error(err);
    document.getElementById("tbody-pistas").innerHTML =
      '<tr><td colspan="6">No se pudo cargar la lista de pistas.</td></tr>';
  });
}

function pintarTablaPistas(pistas) {
  const tbody = document.getElementById("tbody-pistas");

  if (!pistas || pistas.length === 0) {
    tbody.innerHTML = '<tr><td colspan="6">No hay pistas registradas.</td></tr>';
    return;
  }

  tbody.innerHTML = pistas.map(p => `
    <tr>
      <td>${p.nombre}</td>
      <td>${p.ubicacion}</td>
      <td>${p.precioHora} €</td>
      <td>
        <span class="estado ${p.activa ? 'estado-activa' : 'estado-cancelada'}">
          ${p.activa ? 'Activa' : 'Inactiva'}
        </span>
      </td>
      <td>
        <button class="boton boton-secundario"
          onclick="toggleActivarPista(${p.idPista}, ${p.activa}, this)">
          ${p.activa ? 'Desactivar' : 'Activar'}
        </button>
      </td>
    </tr>
  `).join("");
}

function toggleActivarPista(idPista, estaActiva, boton) {
  // PATCH para cambiar el estado activa/inactiva
  fetch(`${API_URL}/pistaPadel/courts/${idPista}`, {
    method: "PATCH",
    headers: { ...authHeader(), "Content-Type": "application/json" },
    body: JSON.stringify({ activa: !estaActiva })
  })
  .then(r => {
    if (!r.ok) throw new Error("Error " + r.status);
    return r.json();
  })
  .then(() => cargarPistas())   // recargar tabla tras el cambio
  .catch(err => {
    console.error(err);
    alert("No se pudo actualizar el estado de la pista.");
  });
}


// ADMIN-RESERVATIONS: cargar todas las reservas con filtros

function iniciarAdminReservations() {
  if (!verificarAdmin()) return;

  // Cargar todas las reservas al entrar
  cargarReservasAdmin();

  // Filtros: se aplican al enviar el formulario
  const form = document.getElementById("form-filtros-admin");
  if (form) {
    form.addEventListener("submit", function (event) {
      event.preventDefault();
      cargarReservasAdmin();
    });
  }
}

function cargarReservasAdmin() {
  const date     = document.getElementById("date")?.value     || "";
  const courtId  = document.getElementById("courtId")?.value  || "";
  const userId   = document.getElementById("userId")?.value   || "";

  let url = `${API_URL}/pistaPadel/admin/reservations?`;
  if (date)    url += `date=${date}&`;
  if (courtId) url += `courtId=${courtId}&`;
  if (userId)  url += `userId=${userId}&`;

  console.log("Cargando reservas admin con filtros:", url);

  fetch(url, { headers: authHeader() })
  .then(r => {
    if (r.status === 403) {
      document.getElementById("tbody-reservas").innerHTML =
        '<tr><td colspan="7">Acceso denegado. Solo administradores.</td></tr>';
      return null;
    }
    if (!r.ok) throw new Error("Error " + r.status);
    return r.json();
  })
  .then(reservas => {
    if (reservas) pintarTablaReservas(reservas);
  })
  .catch(err => {
    console.error(err);
    document.getElementById("tbody-reservas").innerHTML =
      '<tr><td colspan="7">No se pudieron cargar las reservas.</td></tr>';
  });
}

function pintarTablaReservas(reservas) {
  const tbody = document.getElementById("tbody-reservas");

  if (!reservas || reservas.length === 0) {
    tbody.innerHTML = '<tr><td colspan="7">No hay reservas con los filtros aplicados.</td></tr>';
    return;
  }

  tbody.innerHTML = reservas.map(r => `
    <tr>
      <td>#${r.idReserva}</td>
      <td>${r.usuario?.email || r.usuario?.nombre || "—"}</td>
      <td>${r.pista?.nombre || "—"}</td>
      <td>${r.fechaReserva}</td>
      <td>${r.horaInicio}</td>
      <td>${r.duracionMinutos} min</td>
      <td>
        <span class="estado ${r.estado === 'ACTIVA' ? 'estado-activa' : 'estado-cancelada'}">
          ${r.estado}
        </span>
      </td>
    </tr>
  `).join("");
}


// ADMIN-HOME: verificar sesión y mostrar nombre
function iniciarAdminHome() {
  if (!verificarAdmin()) return;

  const email = localStorage.getItem("usuarioEmail");
  const nombreEl = document.getElementById("admin-email");
  if (nombreEl) nombreEl.textContent = email;
}
