console.log("REGISTRO JS CARGADO");
//en intelli j const API_URL = "";
const API_URL = "http://localhost:8080";

// cuando la pagina haya cargado, ejecuta esto:
document.addEventListener("DOMContentLoaded", function () {
  //busca el formulario por su id 
  const form = document.getElementById("form-registro");

  //cuando se envie el formulario, ejecuta esta funcion
  form.addEventListener("submit", function (event) {
    event.preventDefault();
    const datosJsonFormulario = form2json(event);
    registrarUsuario(datosJsonFormulario);
  });
});

//ya tenemos nuestros datos en formato JSON 
function registrarUsuario(datosJsonFormulario) {
  console.log("REGISTRO:", datosJsonFormulario);

  //mandamos peticion y cnectamos con el backend 
  fetch(`${API_URL}/pistaPadel/auth/register`, {
    method: "POST",
    body: datosJsonFormulario,
    headers: {
      "Content-Type": "application/json"
    }
  })
  .then(response => {
    if (response.ok) {
      location.href = "login.html?registrado";
    } else if (response.status === 409) {
      mostrarAviso("✖︎ Usuario ya registrado", "error");
    } else {
      mostrarAviso("✖︎ Error en el registro", "error");
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
  aviso.className = tipo;
}

function form2json(event) {
  //evita que se vaya a otra pagina directamente al enviar el formulario
  event.preventDefault();
  //lee todos los inputs del form y los convierte a un objeto JSON
  const data = new FormData(event.target);
  return JSON.stringify(Object.fromEntries(data.entries()));
}



/*
SI TENEMOS QUE VALIDAR CONTRASEÑA DOS VECES: 

//Validacion frontend, no usa backend , no usa fetch, no guarda nada
//comprueba si las dos contraseñas son iguales al registrarse
function compruebaPass() {
  let correcto = false;
  correcto = document.getElementById("password").value === 
             document.getElementById("password2").value;
  if (correcto) mostrarAviso();
  else mostrarAviso('✖︎ Contraseña inválida', 'error');
  return correcto;
}



METODO GET
async function cargarUsuarios() {

  const res = await fetch('/api/users');

  const usuarios = await res.json();

  console.log(usuarios);



  METODO POST
  async function login(datos) {

  const res = await fetch('/api/login', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(datos)
  });

  if (res.ok) {
    console.log("login correcto");
  }
}



  EN GENERAL USAREMOS:
  async function peticion() {

  try {

    const res = await fetch('/api', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(datos)
    });

    if (res.ok) {

      const json = await res.json();

      console.log(json);

    } else {

      console.log(res.status);

    }

  } catch(error) {

    console.error(error);
  }
}
}*/