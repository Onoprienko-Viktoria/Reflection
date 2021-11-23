package com.luxoft.reflection;

import com.luxoft.testclasses.Employee;
import com.luxoft.testclasses.Person;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;


public class TestReflectionUtils {

    @Test
    void TestCreateObject() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Person person = (Person) ReflectionUtils.create(Person.class);
        assertTrue(person.isCreated());
    }

    @Test
    void TestCanNotCreateObjectWithNotEmptyConstructor() {
        Assertions.assertThrows(NoSuchMethodException.class, () -> {
            Employee employee = (Employee) ReflectionUtils.create(Employee.class);
        });
    }


    @Test
    void testGetMethodsWithFinalSignature() {
        Employee employee = new Employee("Bob", 32, 1, 29, true);
        assertEquals("getRate int arg0 getWorkedHours int arg0 boolean arg1", ReflectionUtils.getFinalMethodsSignatures(employee));
    }

    @Test
    void testGetNonPublicMethods() {
        assertEquals("getRate isWalking", ReflectionUtils.getNonPublicMethods(Employee.class));
    }

    @Test
    void testGetParentsAndInterfaces() {
        assertEquals("interface com.luxoft.testclasses.TestInterface class com.luxoft.testclasses.Person", ReflectionUtils.getParentsAndInterfaces(Employee.class));
    }

    @Test
    void testInvokeMethodsWithoutParameters() throws InvocationTargetException, IllegalAccessException {
        Employee employee = new Employee("Bob", 32, 1, 29, true);
        ReflectionUtils.invokeMethodsWithoutParameters(employee);
        assertFalse(employee.getIsWorking());
    }

    @Test
    void testSetPrivateFieldsOnZeroValue() throws IllegalAccessException {
        Employee employee = new Employee("Bob", 32, 1, 29, true);
        ReflectionUtils.setPrivateFieldsOnZeroValue(employee);
        assertEquals(0, employee.getAge());
        assertEquals(0, employee.getId());
        assertFalse(employee.getIsWorking());
        assertNull(employee.getName());
    }
}
