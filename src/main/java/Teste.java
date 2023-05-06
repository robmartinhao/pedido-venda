import com.algaworks.pedidovenda.model.Cliente;
import com.algaworks.pedidovenda.model.Endereco;
import com.algaworks.pedidovenda.model.TipoPessoa;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;


public class Teste {

    public static void main(String[] args) {

        final EntityManagerFactory factory = Persistence.createEntityManagerFactory("PedidoPU");
        EntityManager manager = factory.createEntityManager();

        EntityTransaction trx = manager.getTransaction();
        trx.begin();

        Cliente cliente = new Cliente();
        cliente.setNome("Joaão da Couves");
        cliente.setEmail("joao@dascouves.com");
        cliente.setDocumentoReceitaFederal("123.123.123-12");
        cliente.setTipo(TipoPessoa.FISICA);

        Endereco endereco = new Endereco();
        endereco.setLogradouro("Rua das Aboboras Vermelhas");
        endereco.setNumero("111");
        endereco.setCidade("Uberlândia");
        endereco.setUf("MG");
        endereco.setCep("38400-000");
        endereco.setCliente(cliente);

        cliente.getEnderecos().add(endereco);

        manager.persist(cliente);

        trx.commit();
    }
}
