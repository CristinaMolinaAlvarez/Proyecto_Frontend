console.log("USER HOME JS CARGADO");

document.addEventListener("DOMContentLoaded", function () {
  const email = localStorage.getItem("usuarioEmail");
  const rol   = localStorage.getItem("usuarioRol");

  if (!email) {
    location.href = "login.html";
    return;
  }

  // Si un admin aterrizó aquí, redirigir a su zona
  if (rol === "ADMIN") {
    location.href = "admin-home.html";
    return;
  }
});

function logout() {
  localStorage.removeItem("usuarioId");
  localStorage.removeItem("usuarioEmail");
  localStorage.removeItem("usuarioPassword");
  localStorage.removeItem("usuarioRol");
  location.href = "login.html";
}
