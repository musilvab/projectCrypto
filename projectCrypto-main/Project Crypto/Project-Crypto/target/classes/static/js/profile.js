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

        fetch(`/users/encripted/${username}`, {
            method: 'GET',
            headers: {
                "Accept": "application/json"
            }
        }).then(response => {
            if (!response.ok) {
                throw new Error("Não há dados para recuperar!")
            }
            return response.json();
        }).then(data => { //dados criptografados

            fetch(`/users/decripted/${username}`, {
                method: 'GET',
                headers: {
                    "Accept": "application/json"
                }
            }).then(response => {
                if (!response.ok) {
                    throw new Error ("Erro ao descriptografar!")
                }
                return response.json();
            }).then(decryptedData => { //dados descriptografados
                console.log(data)
                console.log(decryptedData)
                populateTable(data, decryptedData);
            })
                .catch(error=>{
                    alert("Erro ao descriptografar!")
                })

        })
            .catch(error => {
                alert("Dados estão vazios")
            });
    })


    .catch(error => {
        alert("Erro ao acessar os dados do usuário.");
    });




function populateTable(data, decryptedData) {
    const tableBody = document.getElementById("userDataTable").querySelector("tbody");
    tableBody.innerHTML = "";

    data.forEach((item, index) => {
        const row = document.createElement("tr");

        const cell1 = document.createElement("td");
        const p1 = document.createElement("p");
        p1.className = "table-cell limit-text";
        p1.textContent = item.data;
        cell1.className = `encrypted`
        cell1.appendChild(p1);
        row.appendChild(cell1);

        const cell2 = document.createElement("td");
        const p2 = document.createElement("p");
        cell2.className = "decrypted";
        p2.className = `hidden`;
        p2.textContent = decryptedData[index];
        cell2.appendChild(p2);
        row.appendChild(cell2)

        const cell3 = document.createElement("td");
        const button = document.createElement("button");
        const icon = document.createElement("i");
        icon.className = "fas fa-eye";
        icon.style.color = "gray";
        button.className = "toggle-btn";
        button.appendChild(icon);
        cell3.appendChild(button);
        row.appendChild(cell3);

        tableBody.appendChild(row);
    });

    document.querySelectorAll(".toggle-btn").forEach(button => {
        button.addEventListener("click", function() {
            const row = this.closest("tr");
            const decryptedTd = row.querySelector("td.decrypted");
            const pElement = decryptedTd.querySelector("p");

            if (pElement.className === "hidden") {
                pElement.className = pElement.className.replace("hidden", "table-cell");
            } else {
                pElement.className = pElement.className.replace("table-cell", "hidden");
            }
        });
    });
}




