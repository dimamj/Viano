package Presenter;

import org.junit.*;
import static org.mockito.Mockito.*;
/**
 * Created by dimamj on 03.06.2015.
 */
public class VPresenterTestCase {


    @Test
    public void testValidationAllEmpty(){
        VPresenter presenter = mock(VPresenter.class);
        String[] strings = new String[]{"", "", ""};

        when(presenter.validationInput("add", strings)).thenCallRealMethod();

        Assert.assertFalse(presenter.validationInput("add", strings));

        verify(presenter,times(1)).validationInput("add",strings);
    }

    @Test
    public void testValidationOneEmpty(){
        VPresenter presenter = mock(VPresenter.class);
        String[] strings = new String[]{"1", "", ""};

        when(presenter.validationInput("add", strings)).thenCallRealMethod();

        Assert.assertFalse(presenter.validationInput("add", strings));

        verify(presenter,times(1)).validationInput("add",strings);
    }

    @Test
    public void testValidationCorrectAddApp(){
        VPresenter presenter = mock(VPresenter.class);
        String[] strings = new String[]{"3", "Skype", "скайп","false"};

        when(presenter.validationInput("add", strings)).thenCallRealMethod();

        Assert.assertTrue(presenter.validationInput("add", strings));

        verify(presenter,times(1)).validationInput("add",strings);
    }

    @Test
    public void testValidationIncorrectAddApp(){
        VPresenter presenter = mock(VPresenter.class);
        String[] strings = new String[]{"3", "Skype", "скайп123","false"};

        when(presenter.validationInput("add", strings)).thenCallRealMethod();

        Assert.assertFalse(presenter.validationInput("add", strings));

        verify(presenter,times(1)).validationInput("add",strings);
    }

    @Test
    public void testValidationCorrectAddWeb(){
        VPresenter presenter = mock(VPresenter.class);
        String[] strings = new String[]{"3", "http://www.skype.com", "скайп","true"};

        when(presenter.validationInput("add", strings)).thenCallRealMethod();

        Assert.assertTrue(presenter.validationInput("add", strings));

        verify(presenter,times(1)).validationInput("add",strings);
    }

    @Test
    public void testValidationIncorrectAddWeb(){
        VPresenter presenter = mock(VPresenter.class);
        String[] strings = new String[]{"3", "www.skype.com", "скайп","true"};

        when(presenter.validationInput("add", strings)).thenCallRealMethod();

        Assert.assertFalse(presenter.validationInput("add", strings));

        verify(presenter,times(1)).validationInput("add",strings);
    }



}
