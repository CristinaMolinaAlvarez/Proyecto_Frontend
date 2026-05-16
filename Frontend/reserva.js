console.log("RESERVATION JS CARGADO");
//en intelli j const API_URL = "";
const API_URL = "http://localhost:8080";

// cuando la pagina haya cargado, ejecuta esto:
document.addEventListener("DOMContentLoaded", function () {
  //busca el formulario por su id 
  const form = document.getElementById("form-reserva");

  //cuando se envie el formulario, ejecuta esta funcion
  form.addEventListener("submit", function (event) {
    event.preventDefault();
    //ahora este sera el formulario de pista con su fecha hora y todo 
    const datosJsonFormulario = form2json(event);
    reservar(datosJsonFormulario);
  });


  function reservar(datosJsonFormulario) {
  console.log("RESERVA:", datosJsonFormulario);

  const email = localStorage.getItem("usuarioEmail");
  const password = localStorage.getItem("usuarioPassword");

  console.log("EMAIL AUTH:", email);
  console.log("PASSWORD AUTH:", password);
  //mandamos peticion y cnectamos con el backend 
  fetch(`${API_URL}/pistaPadel/reservations`, {
    method: "POST",
    body: datosJsonFormulario,
    headers: {
      "Content-Type": "application/json",
      // LOGIN BASIC AUTH, forzamos un usuario y contraseña para probar la reserva, en este caso el usuario es
      //TENEMOS QUE CREAR EMAIL Y PASSWORD EN LOCALSTORAGE PARA QUE FUNCIONE, LO HACEMOS EN LOGIN.JS
      "Authorization": "Basic " + btoa(email + ":" + password)    }
  })
  .then(response => {
    console.log("Status reserva:", response.status);
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
  //evita que se vaya a otra pagina directamente al enviar el formulario
  event.preventDefault();
  //lee todos los inputs del form y los convierte a un objeto JSON
  const data = new FormData(event.target);
  return JSON.stringify(Object.fromEntries(data.entries()));
}

});
