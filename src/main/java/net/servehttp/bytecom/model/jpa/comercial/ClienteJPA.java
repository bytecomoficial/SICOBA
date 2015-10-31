package net.servehttp.bytecom.model.jpa.comercial;

import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.expr.BooleanExpression;
import net.servehttp.bytecom.model.jpa.entity.comercial.Cliente;
import net.servehttp.bytecom.model.jpa.entity.comercial.QCliente;
import net.servehttp.bytecom.model.jpa.entity.comercial.StatusCliente;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author clairton
 */
@Transactional
public class ClienteJPA implements Serializable {

    @Inject
    protected EntityManager em;

    private static final long serialVersionUID = 1857140370479772238L;
    private QCliente c = QCliente.cliente;

    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    public List<Cliente> buscarTodosClientePorNomeIp(String nome, String ip, String mac, StatusCliente status) {
        BooleanExpression condicao = c.id.eq(c.id);

        if (nome != null && !nome.trim().isEmpty()) {
            condicao = condicao.and(c.nome.like("%" + nome + "%"));
        }

        if (ip != null && !ip.isEmpty()) {
            condicao = condicao.and(c.conexao.ip.eq(ip));
        }

        if (mac != null && !mac.isEmpty()) {
            condicao = condicao.and(
                    (c.contrato.equipamento.mac.like("%" + mac + "%")
                            .or(c.contrato.equipamentoWifi.mac.like("%" + mac + "%")))
            );
        }

        if (status != null) {
            condicao = condicao.and(c.status.eq(status));
        }

        return new JPAQuery(em).from(c).where(condicao).orderBy(c.nome.asc()).limit(200).list(c);
    }

    public List<Cliente> buscaUltimosClientesAlterados() {
        LocalDateTime data = LocalDateTime.now().minusMonths(2);
        return new JPAQuery(em).from(c).where(c.updatedAt.gt(data)).orderBy(c.updatedAt.desc())
                .limit(20).list(c);
    }
}
