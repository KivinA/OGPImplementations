package chapter_4.course_implementation;
import static org.junit.Assert.*;
import org.junit.*;

public class PersonTest {

    private Person unmarriedMale, otherUnmarriedMale, marriedMale,
            otherMarriedMale, unmarriedFemale, marriedFemale, terminatedFemale;

    @Before
    public void setUp() throws Exception {
        unmarriedMale = new Person(Gender.MALE);
        otherUnmarriedMale = new Person(Gender.MALE);
        marriedMale = new Person(Gender.MALE, new Person());
        otherMarriedMale = new Person(Gender.MALE, new Person());
        unmarriedFemale = new Person();
        marriedFemale = new Person(Gender.FEMALE, new Person(Gender.MALE));
        terminatedFemale = new Person();
        terminatedFemale.terminate();
    }

    @Test
    public void isValidGender_MaleCase() {
        assertTrue(Person.isValidGender(Gender.MALE));
    }

    @Test
    public void isValidGender_FemaleCase() {
        assertTrue(Person.isValidGender(Gender.FEMALE));
    }

    @Test
    public void isValidGender_NonEffectiveGender() {
        assertFalse(Person.isValidGender(null));
    }

    @Test
    public void isFemale_TrueCase() {
        assertTrue(unmarriedFemale.isFemale());
    }

    @Test
    public void isFemale_FalseCase() {
        assertFalse(unmarriedMale.isFemale());
    }

    @Test
    public void isMale_TrueCase() {
        assertTrue(unmarriedMale.isMale());
    }

    @Test
    public void isMale_FalseCase() {
        assertFalse(unmarriedFemale.isMale());
    }

    @Test
    public void extendedConstructor_NonEffectivePartner() throws Exception {
        Person thePerson = new Person(Gender.MALE, null);
        assertEquals(Gender.MALE, thePerson.getGender());
        assertNull(thePerson.getSpouse());
        assertFalse(thePerson.isTerminated());
    }

    @Test
    public void extendedConstructor_LegalEffectivePartner() throws Exception {
        Person thePartner = new Person(Gender.MALE);
        Person thePerson = new Person(Gender.FEMALE, thePartner);
        assertEquals(Gender.FEMALE, thePerson.getGender());
        assertEquals(thePartner, thePerson.getSpouse());
        assertEquals(thePerson, thePartner.getSpouse());
        assertFalse(thePerson.isTerminated());
    }

    @Test(expected = IllegalArgumentException.class)
    public void extendedConstructor_IllegalGender() throws Exception {
        new Person(null, null);
    }

    @Test(expected = IllegalPartnerException.class)
    public void extendedConstructor_UnacceptablePartner() throws Exception {
        new Person(Gender.MALE, terminatedFemale);
    }

    @Test(expected = IllegalPartnerException.class)
    public void extendedConstructor_SameGender() throws Exception {
        new Person(Gender.MALE, unmarriedMale);
    }

    @Test(expected = IllegalPartnerException.class)
    public void extendedConstructor_MarriedPartner() throws Exception {
        new Person(Gender.MALE, marriedMale);
    }

    @Test
    public void middleConstructor_LegalGender() throws Exception {
        Person thePerson = new Person(Gender.MALE);
        assertEquals(Gender.MALE, thePerson.getGender());
        assertNull(thePerson.getSpouse());
        assertFalse(thePerson.isTerminated());
    }

    @Test(expected = IllegalArgumentException.class)
    public void middleConstructor_IllegalGender() throws Exception {
        new Person(null);
        fail("Exception expected!");
    }

    @Test
    public void defaultConstructor_SingleCase() {
        Person thePerson = new Person();
        assertEquals(Gender.FEMALE, thePerson.getGender());
        assertNull(thePerson.getSpouse());
        assertFalse(thePerson.isTerminated());
    }

    @Test
    public void isMarried_FalseCase() {
        assertFalse(unmarriedMale.isMarried());
    }

    @Test
    public void isMarried_TrueCase() {
        assertTrue(marriedMale.isMarried());
    }

    @Test
    public void canHaveAsSpouse_NonEffectiveSpouse() {
        assertTrue(unmarriedMale.canHaveAsSpouse(null, null));
    }

    @Test
    public void canHaveAsSpouse_TerminatedPersonEffectiveSpouse() {
        assertFalse(terminatedFemale.canHaveAsSpouse(unmarriedMale,
            Gender.FEMALE));
    }

    @Test
    public void canHaveAsSpouse_TerminatedPersonNonEffectiveSpouse() {
        // This test is not really needed in a pure black-box test.
        assertTrue(terminatedFemale.canHaveAsSpouse(null, null));
    }

    @Test
    public void canHaveAsSpouse_TerminatedSpouse() {
        assertFalse(unmarriedMale
            .canHaveAsSpouse(terminatedFemale, Gender.MALE));
    }

    @Test
    public void canHaveAsSpouse_SameGenders() {
        assertFalse(unmarriedMale.canHaveAsSpouse(otherUnmarriedMale,
            Gender.MALE));
    }

    @Test
    public void canHaveAsSpouse_InvalidGender() {
        assertFalse(unmarriedMale.canHaveAsSpouse(unmarriedFemale, null));
    }

    @Test
    public void canHaveAsSpouse_DifferentGenders() {
        assertTrue(unmarriedMale.canHaveAsSpouse(unmarriedFemale, Gender.MALE));
    }

    @Test
    public void hasProperSpouse_UnmarriedPerson() {
        assertTrue(unmarriedFemale.hasProperSpouse());
    }

    @Test
    public void hasProperSpouse_MarriedPerson() {
        assertTrue(marriedFemale.hasProperSpouse());
    }

    @Test
    public void marry_LegalCase() throws Exception {
        unmarriedMale.marry(unmarriedFemale);
        assertEquals(unmarriedFemale, unmarriedMale.getSpouse());
        assertEquals(unmarriedMale, unmarriedFemale.getSpouse());
    }

    @Test(expected = IllegalPartnerException.class)
    public void marry_NonEffectiveSpouse() throws Exception {
        unmarriedMale.marry(null);
        fail("Exception Expected!");
    }

    @Test(expected = IllegalPartnerException.class)
    public void marry_UnacceptableSpouse() throws Exception {
        unmarriedMale.marry(otherUnmarriedMale);
        fail("Exception Expected!");
    }

    @Test(expected = IllegalStateException.class)
    public void marry_AlreadyMarried() throws Exception {
        marriedMale.marry(unmarriedFemale);
        fail("Exception Expected!");
    }

    @Test(expected = IllegalStateException.class)
    public void marry_PartnerAlreadyMarried() throws Exception {
        unmarriedMale.marry(marriedFemale);
        fail("Exception Expected!");
    }

    @Test
    public void divorce_MarriedPerson() {
        Person spouseOfMarriedMale = marriedMale.getSpouse();
        marriedMale.divorce();
        assertFalse(marriedMale.isMarried());
        assertFalse(spouseOfMarriedMale.isMarried());
    }

    @Test
    public void divorce_UnmarriedPerson() {
        unmarriedMale.divorce();
        assertFalse(unmarriedMale.isMarried());
    }

    @Test
    public void terminate_MarriedPerson() {
        Person spouseOfMarriedMale = marriedMale.getSpouse();
        marriedMale.terminate();
        assertTrue(marriedMale.isTerminated());
        assertFalse(marriedMale.isMarried());
        assertFalse(spouseOfMarriedMale.isMarried());
    }

    @Test
    public void terminate_UnmarriedPerson() {
        unmarriedMale.terminate();
        assertTrue(unmarriedMale.isTerminated());
        assertFalse(unmarriedMale.isMarried());
    }

    @Test
    public void switchPartnerWith_UnmarriedPersonsSameGender() throws Exception {
        unmarriedMale.switchPartnerWith(otherUnmarriedMale);
        // Literal postcondition 1 (other postconditions must be worked out still)
        assertTrue((unmarriedMale.getSpouse() == otherUnmarriedMale)
            || (unmarriedMale.getSpouse() == otherUnmarriedMale.getSpouse()));
        // Expected effects for this invocation in a more compact way
        assertTrue(!unmarriedMale.isMarried());
        assertTrue(!otherUnmarriedMale.isMarried());
    }

    @Test
    public void switchPartnerWith_UnmarriedPersonsDifferentGender()
            throws Exception {
        unmarriedFemale.switchPartnerWith(unmarriedMale);
        // Literal postcondition 1 (other postconditions must be worked out still)
        assertTrue((unmarriedFemale.getSpouse() == unmarriedMale)
            || (unmarriedFemale.getSpouse() == unmarriedMale.getSpouse()));
        // Expected effects for this invocation in a more compact way
        assertTrue((unmarriedFemale.getSpouse() != unmarriedMale)
            || (!unmarriedFemale.isMarried()));
        assertTrue((unmarriedMale.getSpouse() != unmarriedFemale)
            || (!unmarriedMale.isMarried()));
    }

    @Test
    public void swicthPartnerWith_MarriedPersonsSameGender() throws Exception {
        Person spouseOfMalePerson = marriedMale.getSpouse();
        Person spouseOfOtherMalePerson = otherMarriedMale.getSpouse();
        marriedMale.switchPartnerWith(otherMarriedMale);
        // Literal postcondition 1 (other postconditions must be worked out still)
        assertTrue((marriedMale.getSpouse() == otherMarriedMale)
            || (marriedMale.getSpouse() == spouseOfOtherMalePerson));
        // Expected effects for this invocation in a more compact way
        assertTrue(marriedMale.getSpouse() == spouseOfOtherMalePerson);
        assertTrue(spouseOfMalePerson.getSpouse() == otherMarriedMale);
        assertTrue(otherMarriedMale.getSpouse() == spouseOfMalePerson);
        assertTrue(spouseOfOtherMalePerson.getSpouse() == marriedMale);
    }

    @Test
    public void switchPartnerWith_MarriedPersonsDifferentGender()
            throws Exception {
        Person spouseOfFemalePerson = marriedFemale.getSpouse();
        Person spouseOfMalePerson = marriedMale.getSpouse();
        marriedFemale.switchPartnerWith(marriedMale);
        // Literal postcondition 1 (other postconditions must be worked out still)
        assertTrue((marriedFemale.getSpouse() == marriedMale)
            || (marriedFemale.getSpouse() == spouseOfMalePerson));
        // Expected effects for this invocation in a more compact way
        assertTrue(marriedFemale.getSpouse() == marriedMale);
        assertTrue(spouseOfFemalePerson.getSpouse() == spouseOfMalePerson);
        assertTrue(marriedMale.getSpouse() == marriedFemale);
        assertTrue(spouseOfMalePerson.getSpouse() == spouseOfFemalePerson);
    }

    @Test
    public void swtichPartnerWith_OnePersonUnmarried() throws Exception {
        Person spouseOfMarriedPerson = marriedFemale.getSpouse();
        unmarriedFemale.switchPartnerWith(marriedFemale);
        // Literal postcondition 1 (other postconditions must be worked out still)
        assertTrue((unmarriedFemale.getSpouse() == marriedFemale)
            || (unmarriedFemale.getSpouse() == spouseOfMarriedPerson));
        // Expected effects for this invocation in a more compact way
        assertTrue(unmarriedFemale.getSpouse() == spouseOfMarriedPerson);
        assertTrue(!marriedFemale.isMarried());
        assertTrue(spouseOfMarriedPerson.getSpouse() == unmarriedFemale);
    }

    @Test
    public void switchPartnerWith_OtherPersonUnmarried() throws Exception {
        Person spouseOfMarriedPerson = marriedFemale.getSpouse();
        marriedFemale.switchPartnerWith(unmarriedMale);
        // Literal postcondition 1 (other postconditions must be worked out still)
        assertTrue((marriedFemale.getSpouse() == unmarriedMale)
            || (marriedFemale.getSpouse() == unmarriedMale.getSpouse()));
        // Expected effects for this invocation in a more compact way
        assertTrue(marriedFemale.getSpouse() == unmarriedMale);
        assertTrue(!spouseOfMarriedPerson.isMarried());
        assertTrue(unmarriedMale.getSpouse() == marriedFemale);
    }

    @Test(expected = IllegalArgumentException.class)
    public void switchPartnerWith_NonEffectivePerson() throws Exception {
        unmarriedFemale.switchPartnerWith(null);
        fail("Exception Expected!");
    }

}
