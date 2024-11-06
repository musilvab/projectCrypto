const username = document.getElementById("username").innerText;
let userData = null;

fetch(`/users/${username}`, {
    method: 'GET',
    headers: {
        "Accept": "application/json"
    }
}).then(response => {
    if (!response.ok) {
        throw new Error('Erro ao buscar dados do usuário');
    }
    return response.json();
})
    .then(data => {
        userData = data;
    })
    .catch(error => {
        console.error("Erro:", error);
        alert("Erro ao acessar os dados do usuário.");
    });

fetch(`/users/encripted/${username}`, {
    method: 'GET',
    headers: {
        "Accept": "application/json"
    }
}).then(response => {
    if (!response.ok) {
        throw new Error("Erro ao acessar os dados")
    }
    return response.json();
}).then(data => {
    console.log(data)
    populateTable(data);
})
.catch(error => {
    console.log("Erro:", error)
    alert("Erro ao acessar os dados")
});

function populateTable(data) {
    const tableBody = document.getElementById("userDataTable").querySelector("tbody");
    tableBody.innerHTML = "";

    data.forEach(item => {
        const row = document.createElement("tr");

        const cell1 = document.createElement("td");
        cell1.textContent = item.id;
        row.appendChild(cell1);

        const cell2 = document.createElement("td");
        cell2.textContent = item.data;
        row.appendChild(cell2);

        const cell3 = document.createElement("td");
        const icon = document.createElement("i");
        icon.className = "fas fa-eye";
        icon.style.color = "gray";
        cell3.appendChild(icon);
        row.appendChild(cell3);

        tableBody.appendChild(row);
    });
}
