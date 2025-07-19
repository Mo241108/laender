// 1. Liste der gültigen Länder laden (extern oder lokal als Array):
import validCountries from './countries.js'; 
// optional: lade ISO‑Länderliste als JSON-Datei

const status = document.getElementById('status');
const eingabe = document.getElementById('eingabe');
const btnAdd = document.getElementById('hinzufuegen');
const btnDone = document.getElementById('fertig');
const modal = document.getElementById('zusammenfassung');
const listeEl = document.getElementById('liste');
const btnClose = document.getElementById('schliessen');

const laenderSet = new Set();

function capitalize(text) {
  return text.charAt(0).toUpperCase() + text.slice(1);
}

btnAdd.addEventListener('click', () => {
  const land = eingabe.value.trim();
  if (!land) {
    status.textContent = 'Bitte gib zuerst ein Land ein!';
  } else if (!validCountries.includes(land.toLowerCase())) {
    status.textContent = `'${land}' ist kein gültiges Land.`;
  } else if (laenderSet.has(land.toLowerCase())) {
    status.textContent = `Das Land '${land}' wurde schon genannt.`;
  } else {
    laenderSet.add(land.toLowerCase());
    status.textContent = `'${land}' hinzugefügt. Insgesamt: ${laenderSet.size}`;
  }
  eingabe.value = '';
  eingabe.focus();
});

btnDone.addEventListener('click', () => {
  let text = `Du hast insgesamt ${laenderSet.size} verschiedene Länder genannt:\n\n`;
  let i = 1;
  for (let land of laenderSet) {
    text += `${i++}. ${capitalize(land)}\n`;
  }
  listeEl.textContent = text;
  modal.classList.remove('hidden');
});

btnClose.addEventListener('click', () => {
  modal.classList.add('hidden');
  // Falls gewünscht: Seite neu laden
  // window.location.reload();
});
