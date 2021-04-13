//Listeners
document.getElementById("nombre").addEventListener('change', validarNombre);
document.getElementById("apellidos").addEventListener('change', validarApellidos);
document.getElementById("correo").addEventListener('change', validarCorreo);
document.getElementById("telefono").addEventListener('change', validarTelefono);
document.getElementById("numper").addEventListener('change', validarNumPersonas);
document.getElementById("fecha").addEventListener('change', validarFecha);
document.getElementById("horaent").addEventListener('change', validarHoras);
document.getElementById("horasal").addEventListener('change', validarHoras);
document.getElementById("sala").addEventListener('change', modificarNumPersonas);
document.getElementById("btnSubmit").addEventListener('click', validar);

//Comprobar campos para enviar
function validar() {

    if (validarNombre() && validarApellidos() && validarCorreo() && validarTelefono() && validarNumPersonas() && validarFecha() && validarHoras()) {
        Swal.fire({
            icon: 'success',
            title: 'Reserva completada con éxito',
            // confirmButtonText: 'Aceptar',
            timerProgressBar: true,
            showConfirmButton: false,
            timer: 3000,
            allowOutsideClick: false
        });
        setTimeout(() => {
            document.getElementById("reservaForm").submit();
        }, 3000);
    } else {

        Swal.fire({
            icon: 'error',
            title: 'Vaya...',
            text: 'Parece que algún campo es incorrecto',
            confirmButtonText: 'Aceptar',
            confirmButtonColor: '#a4b5c8'
        });
        return false;
    }
}

//Validar nombre
function validarNombre() {
    let nombre = document.getElementById("nombre").value;
    let nombreER = /^([A-ZÁÉÍÓÚ]{1}[a-zñáéíóú]+[\s]*)+$/;
    if (nombre == null || nombre.length == 0 || !nombreER.test(nombre)) {
        document.getElementById("error_nombre").style.display = "block";
        return false;
    } else {
        document.getElementById("error_nombre").style.display = "none";
        return true;
    }
}

//Validar apellidos
function validarApellidos() {
    let apellidos = document.getElementById("apellidos").value;
    let apellidosER = /^([A-ZÁÉÍÓÚ]{1}[a-zñáéíóú]+[\s]*)+$/;
    if (apellidos == null || apellidos.length == 0 || !apellidosER.test(apellidos)) {
        document.getElementById("error_apellidos").style.display = "block";
        return false;
    } else {
        document.getElementById("error_apellidos").style.display = "none";
        return true;
    }
}

//Validar correo
function validarCorreo() {
    let correo = document.getElementById("correo").value;
    let correoER = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/;
    if (correo == null || correo.length == 0 || !correoER.test(correo)) {
        document.getElementById("error_correo").style.display = "block";
        return false;
    } else {
        document.getElementById("error_correo").style.display = "none";
        return true;
    }
}

//Validar telefono
function validarTelefono() {
    let telefono = document.getElementById("telefono").value;
    let telefonoER = /^\d{9}$/;
    if (telefono == null || telefono.length == 0 || !telefonoER.test(telefono)) {
        document.getElementById("error_telefono").style.display = "block";
        return false;
    } else {
        document.getElementById("error_telefono").style.display = "none";
        return true;
    }
}

// Validar número de personas
function validarNumPersonas() {
    let sala = document.getElementById("sala").value;
    let personas = document.getElementById("numper").value;
    if (sala == 1) {
        if (personas < 1 || personas > 4) {
            document.getElementById("error_personas").style.display = "block";
            return false;
        } else {
            document.getElementById("error_personas").style.display = "none";
            return true;
        }
    } else if (sala == 2) {
        if (personas < 1 || personas > 8) {
            document.getElementById("error_personas").style.display = "block";
            return false;
        } else {
            document.getElementById("error_personas").style.display = "none";
            return true;
        }
    } else if (sala == 3) {
        if (personas < 1 || personas > 5) {
            document.getElementById("error_personas").style.display = "block";
            return false;
        } else {
            document.getElementById("error_personas").style.display = "none";
            return true;
        }
    }
}

//Validar fecha
function validarFecha() {
    let actual = new Date();
    let seleccionada = document.getElementById("fecha").value;
    let separado = seleccionada.split("-");
    let formato_fecha = separado[0] + "-" + separado[1] + "-" + separado[2];
    let seleccion = new Date(formato_fecha);
    console.log(formato_fecha);
    if (seleccion <= actual || seleccionada == "") {
        document.getElementById("error_fecha").style.display = "block";
        return false;
    } else {
        document.getElementById("error_fecha").style.display = "none";
        return true;
    }
}

//Validar horas
function validarHoras() {
    let actual = new Date();
    let sActual = actual.getFullYear() + "-" + (actual.getMonth() + 1) +
        "-" + actual.getDate()

    let hora_entrada = document.getElementById("horaent").value;
    let hora_salida = document.getElementById("horasal").value;

    let apertura = new Date(sActual + " " + "08:00");
    let cierre = new Date(sActual + " " + "22:00");

    let entrada = new Date(sActual + " " + hora_entrada);
    let salida = new Date(sActual + " " + hora_salida);

    if (entrada < apertura || cierre < salida || salida <= entrada ||
        hora_entrada == "" || hora_salida == "") {
        document.getElementById("error_horas").style.display = "block";
        return false;
    } else {
        document.getElementById("error_horas").style.display = "none";
        return true;
    }
}

function modificarNumPersonas() {
    // let text = ;
    let sala = document.getElementById("sala").value;
    console.log(sala);
    if (sala == 1)
        document.getElementById("numperlabel").innerHTML = "N. de personas (4 max.)";
    else if (sala == 2)
        document.getElementById("numperlabel").innerHTML = "N. de personas (8 max.)";
    else if (sala == 3)
        document.getElementById("numperlabel").innerHTML = "N. de personas (5 max.)";
    else
        document.getElementById("numperlabel").innerHTML = "N. de personas (max.)";
}