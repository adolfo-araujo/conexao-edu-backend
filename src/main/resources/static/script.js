const API_BASE = window.location.origin;

function getToken() {
  return localStorage.getItem("token");
}

function getSession() {
  return JSON.parse(localStorage.getItem("session") || "null");
}

function saveAuth(auth) {
  localStorage.setItem("token", auth.token);
  localStorage.setItem("session", JSON.stringify(auth.usuario));
}

function clearAuth() {
  localStorage.removeItem("token");
  localStorage.removeItem("session");
}

function toggleScreen(hide, show) {
  const hiddenForm = document.querySelector(".form-" + hide);
  const shownForm = document.querySelector(".form-" + show);
  if (!hiddenForm || !shownForm) return;
  hiddenForm.style.display = "none";
  shownForm.style.display = "block";
  shownForm.querySelectorAll("input").forEach(i => i.value = "");
}

async function apiFetch(path, options = {}) {
  const headers = { ...(options.headers || {}) };
  const token = getToken();
  if (!(options.body instanceof FormData)) {
    headers["Content-Type"] = headers["Content-Type"] || "application/json";
  }
  if (token) headers.Authorization = "Bearer " + token;

  const response = await fetch(API_BASE + path, { ...options, headers });
  const text = await response.text();
  let data = null;

  try {
    data = text ? JSON.parse(text) : null;
  } catch {
    data = text;
  }

  if (!response.ok) {
    const message = data?.message || data?.error || (typeof data === "string" ? data : "Erro na requisição");
    throw new Error(message);
  }

  return data;
}

async function cadastrar() {
  const inputs = document.querySelector(".form-cadastro")?.querySelectorAll("input");
  if (!inputs) return;

  const nome = inputs[0].value.trim();
  const email = inputs[1].value.trim();
  const senha = inputs[2].value.trim();

  if (!nome || !email || !senha) return alert("Preencha todos os campos!");

  try {
    await apiFetch("/auth/register", {
      method: "POST",
      body: JSON.stringify({ nome, email, senha })
    });

    alert("Conta criada com sucesso!");

    // 🔥 VOLTA PRO LOGIN (IMPORTANTE)
    mostrarLogin();

  } catch (error) {
    alert(error.message || "Erro ao cadastrar.");
  }
}

async function fazerLogin() {
  const inputs = document.querySelector(".form-login")?.querySelectorAll("input");
  if (!inputs) return;

  const email = inputs[0].value.trim();
  const senha = inputs[1].value.trim();

  if (!email || !senha) return alert("Preencha todos os campos!");

  try {
    const auth = await apiFetch("/auth/login", {
      method: "POST",
      body: JSON.stringify({ email, senha })
    });

    saveAuth(auth);

    // 🔥 ENTRA NO APP
    window.location.href = "app.html";

  } catch (error) {
    alert(error.message || "Erro ao fazer login.");
  }
}

async function verificarSessao() {
  const isAppPage = location.pathname.endsWith("/app.html") || location.pathname.endsWith("app.html");
  const isIndexPage = location.pathname.endsWith("/index.html") || location.pathname === "/" || location.pathname.endsWith("/");
  const token = getToken();

  if (!token) {
    if (isAppPage) window.location.href = "index.html";
    return;
  }

  try {
    const auth = await apiFetch("/auth/me");
    saveAuth(auth);

    if (isIndexPage && !isAppPage) {
      window.location.href = "app.html";
      return;
    }

    updateGreeting();
    if (isAppPage) await carregarDadosApp();
  } catch {
    clearAuth();
    if (isAppPage) window.location.href = "index.html";
  }
}

function mostrarCadastro() { toggleScreen("login", "cadastro"); }
function mostrarLogin() { toggleScreen("cadastro", "login"); }

function fazerLogout() {
  clearAuth();
  window.location.href = "index.html";
}

function navigate(pageId) {
  document.querySelectorAll(".page").forEach(p => p.classList.remove("page--active"));
  document.getElementById("page-" + pageId)?.classList.add("page--active");
  document.querySelectorAll(".nav-item").forEach(n => n.classList.remove("nav-item--active"));
  const map = { feed: ".nav-inicio", disciplinas: ".nav-disciplinas", calendario: ".nav-calendario", ranking: ".nav-ranking" };
  document.querySelector(map[pageId])?.classList.add("nav-item--active");
}

function updateGreeting() {
  const session = getSession();
  const greeting = document.getElementById("greeting-app");
  if (session && greeting) greeting.textContent = "Olá, " + session.nome + "!";
}

function renderPosts(posts) {
  const container = document.getElementById("community-list");
  if (!container) return;

  if (!posts?.length) {
    container.innerHTML = '<p class="comentario-texto">Nenhuma publicação encontrada.</p>';
    return;
  }

  container.innerHTML = posts.map(post => {
    const raw = (post.conteudo || "").trim();
    const parts = raw.split(":");
    const usuario = parts.length > 1 ? parts.shift().trim() : "Comunidade";
    const texto = parts.length ? parts.join(":").trim() : raw;
    return `
      <div class="comentario">
        <img src="assets/Calendario/user.svg" alt="" class="comentario-avatar-svg">
        <div><span class="comentario-user">${usuario}:</span> <span class="comentario-texto">${texto}</span></div>
      </div>`;
  }).join("");
}

function renderCursos(cursos) {
  const container = document.getElementById("disciplinas-list");
  if (!container) return;

  if (!cursos?.length) {
    container.innerHTML = "<p>Nenhuma disciplina encontrada.</p>";
    return;
  }

  const icons = [
    "assets/Disciplinas/tabler_square-root-2.svg",
    "assets/Disciplinas/icomoon-free_lab.svg",
    "assets/Disciplinas/fluent-emoji-high-contrast_world-map.svg",
    "assets/Disciplinas/mdi_art.svg",
    "assets/Disciplinas/streamline_dictionary-language-book.svg",
    "assets/Disciplinas/mingcute_paper-line.svg"
  ];

  container.innerHTML = cursos.map((curso, index) => `
    <div class="disc-card" title="${curso.descricao || ""}">
      <img class="disc-icon" src="${icons[index % icons.length]}" alt="">
      <span class="disc-card-name">${curso.nome}</span>
    </div>
  `).join("");
}

async function carregarDadosApp() {
  try {
    const [posts, cursos] = await Promise.all([
      apiFetch("/posts"),
      apiFetch("/cursos")
    ]);
    renderPosts(posts);
    renderCursos(cursos);
  } catch (error) {
    console.error(error);
    alert("Não foi possível carregar os dados do aplicativo.");
  }
}
