function atualizarCarrinho() {
    fetch('/carrinho/api/carrinho/itens')
        .then(response => response.json())
        .then(data => {
            // Atualiza o número de itens no carrinho
            document.querySelector('#numero-itens-carrinho').textContent = data;
        })
        .catch(error => console.error('Erro ao atualizar carrinho:', error));
}

// Chama a função para atualizar o carrinho na carga inicial da página
document.addEventListener('DOMContentLoaded', function() {
    atualizarCarrinho();
});

