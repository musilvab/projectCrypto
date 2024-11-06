fetch('/home', {
    method: 'GET',
})
    .then(response => {
        if (response.ok) {
            return response.text();
        }
        throw new Error('Falha ao acessar a página');
    })
    .then(async html => {
        document.body.innerHTML = await html;
    })
    .catch(error => {
        console.error("Erro:", error);
        alert("Erro ao acessar a página.");
    });

