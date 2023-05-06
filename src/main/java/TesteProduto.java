import com.algaworks.pedidovenda.model.Categoria;
import com.algaworks.pedidovenda.model.Produto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.math.BigDecimal;


public class TesteProduto {

    public static void main(String[] args) {

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("PedidoPU");
        EntityManager manager = factory.createEntityManager();

        EntityTransaction trx = manager.getTransaction();
        trx.begin();

// instanciamos a categoria pai (Bebidas)
        Categoria categoriaPai = new Categoria();
        categoriaPai.setDescricao("Bebidas");

// instanciamos a categoria filha (Refrigerantes)
        Categoria categoriaFilha = new Categoria();
        categoriaFilha.setDescricao("Refrigerantes");
        categoriaFilha.setCategoriaPai(categoriaPai);

// adicionamos a categoria Refrigerantes como filha de Bebidas
        categoriaPai.getSubcategorias().add(categoriaFilha);

// ao persistir a categoria pai (Refrigerantes), a filha (Bebidas)
// deve ser persistida também
        manager.persist(categoriaPai);

// instanciamos e persistimos um produto
        Produto produto = new Produto();
        produto.setCategoria(categoriaFilha);
        produto.setNome("Guaraná 2L");
        produto.setQuantidadeEstoque(10);
        produto.setSku("GUA00123");
        produto.setValorUnitario(new BigDecimal(2.21));

        manager.persist(produto);

        trx.commit();

    }
}
