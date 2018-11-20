package droolscourse;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kie.api.event.rule.ObjectDeletedEvent;
import org.kie.api.event.rule.ObjectInsertedEvent;
import org.kie.api.event.rule.ObjectUpdatedEvent;
import org.kie.api.event.rule.RuleRuntimeEventListener;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;
import org.kie.api.runtime.rule.FactHandle;

import droolscourse.model.Account;
import droolscourse.model.CashFlow;
import droolscourse.util.KnowledgeSessionHelper;
import droolscourse.util.OutputDisplay;

public class TestLesson1 {

  static KieContainer kieContainer;
  StatelessKieSession sessionStateless = null;
  KieSession sessionStatefull = null;

  @BeforeClass
  public static void beforeClass() throws Exception {
    kieContainer = KnowledgeSessionHelper.createRuleBase();
  }

  @Before
  public void setUp() {
    System.out.println("----------------------Before------------------------");
  }

  @After
  public void tearDown() {
    System.out.println("----------------------After----------------------\n\n");
  }

  @Test
  public void testFirstRule() {
    System.out.println("testFirstRule:");

    sessionStatefull = KnowledgeSessionHelper.getStatefulKnowledgeSession(kieContainer, "ks-rules-lesson1");

    OutputDisplay outputDisplay = new OutputDisplay();
    sessionStatefull.setGlobal("showResults", outputDisplay);

    Account a = new Account();
    sessionStatefull.insert(a);
    sessionStatefull.fireAllRules();

  }

  @Test
  public void testFirstRuleFireJustOnce() {
    System.out.println("testFirstRuleFireJustOnce:");

    sessionStatefull = KnowledgeSessionHelper.getStatefulKnowledgeSession(kieContainer, "ks-rules-lesson1");

    Account a = new Account();
    sessionStatefull.insert(a);
    System.out.println("- fireAllRules1");
    sessionStatefull.fireAllRules();
    a.setBalance(12.0);
    System.out.println("- fireAllRules2");
    sessionStatefull.fireAllRules();

  }

  @Test
  public void testFirstRuleFireTwice() {
    System.out.println("testFirstRuleFireTwice:");

    sessionStatefull = KnowledgeSessionHelper.getStatefulKnowledgeSession(kieContainer, "ks-rules-lesson1");
    OutputDisplay outputDisplay = new OutputDisplay();
    sessionStatefull.setGlobal("showResults", outputDisplay);

    Account a = new Account();
    FactHandle fact = sessionStatefull.insert(a);
    System.out.println("- fireAllRules1");
    sessionStatefull.fireAllRules();

    a.setBalance(12.0);
    sessionStatefull.update(fact, a);

    System.out.println("- fireAllRules2");
    sessionStatefull.fireAllRules();

  }

  @Test
  public void testUseOfCallBackAnyRuleFired() {
    System.out.println("testUseOfCallBackAnyRuleFired:");

    sessionStatefull = KnowledgeSessionHelper.getStatefulKnowledgeSession(kieContainer, "ks-rules-lesson1");

    sessionStatefull.addEventListener(new RuleRuntimeEventListener() {
      public void objectInserted(ObjectInsertedEvent event) {
        System.out.println("Object inserted: " + event.getObject().toString());
      }

      public void objectUpdated(ObjectUpdatedEvent event) {
        System.out.println("Object was updated: " + event.getObject().toString());
      }

      public void objectDeleted(ObjectDeletedEvent event) {
        System.out.println("Object retracted: " + event.getOldObject().toString());
      }
    });

    Account a = new Account();
    a.setAccountNo(10);

    FactHandle handleAccountA = sessionStatefull.insert(a);
    a.setBalance(12.0);
    sessionStatefull.update(handleAccountA, a);
    sessionStatefull.delete(handleAccountA);

    sessionStatefull.fireAllRules();

  }

  @Test
  public void testSecondRuleInsertObjectsInLHS() {
    System.out.println("testSecondRuleInsertObjectsInLHS:");

    sessionStatefull = KnowledgeSessionHelper.getStatefulKnowledgeSession(kieContainer, "ks-rules-lesson1");

    OutputDisplay outputDisplay = new OutputDisplay();
    sessionStatefull.setGlobal("showResults", outputDisplay);

    sessionStatefull.addEventListener(new RuleRuntimeEventListener() {
      public void objectInserted(ObjectInsertedEvent event) {
        System.out.println("Object inserted: " + event.getObject().toString());
      }

      public void objectUpdated(ObjectUpdatedEvent event) {
        System.out.println("Object was updated: " + event.getObject().toString());
      }

      public void objectDeleted(ObjectDeletedEvent event) {
        System.out.println("Object retracted: " + event.getOldObject().toString());
      }
    });

    CashFlow cf = new CashFlow();
    sessionStatefull.insert(cf);
    sessionStatefull.fireAllRules();

  }

}
