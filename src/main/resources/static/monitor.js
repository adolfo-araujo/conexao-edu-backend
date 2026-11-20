const API_BASE = window.location.origin;

async function carregarEventos() {
  const container = document.getElementById("eventos-list");
  if (!container) return;

  try {
    const response = await fetch(API_BASE + "/monitor/eventos");
    const eventos = await response.json();

    if (!eventos.length) {
      container.innerHTML = '<p class="vazio">Nenhum evento registrado ainda. Faça login ou cadastre um curso no app.</p>';
      return;
    }

    container.innerHTML = eventos.map(evento =>
      `<div class="evento-item">${evento}</div>`
    ).join("");
  } catch (error) {
    container.innerHTML = '<p class="vazio">Não foi possível carregar os eventos.</p>';
    console.error(error);
  }
}

function iniciarMonitor() {
  carregarEventos();
  setInterval(carregarEventos, 3000);
}
