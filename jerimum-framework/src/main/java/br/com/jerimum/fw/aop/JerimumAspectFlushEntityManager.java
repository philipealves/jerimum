package br.com.jerimum.fw.aop;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.aspectj.lang.JoinPoint;
import org.hibernate.internal.SessionImpl;

/**
 * Aspect to intercept and flush entity manager transactions.
 * 
 * @author Dali Freire - dalifreire@gmail.com
 */
public class JerimumAspectFlushEntityManager {

    @PersistenceContext
    private EntityManager em;

    /**
     * Flush the entity manager if some transaction is in progress.
     * 
     * @param jp
     * @throws Exception
     */
    protected void flush(JoinPoint jp) throws Exception {
        if (isTransactionInProgress()) {
            this.em.flush();
        }
    }

    /**
     * Check if some transaction is in progress.
     * 
     * @return boolean
     */
    protected boolean isTransactionInProgress() {
        SessionImpl session = (SessionImpl) this.em.getDelegate();
        return session.isTransactionInProgress();
    }

}
