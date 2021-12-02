package bg.wallet.www.project.services.impl;

import bg.wallet.www.project.models.Quote;
import bg.wallet.www.project.models.view.QuoteViewModel;
import bg.wallet.www.project.repositories.QuoteRepository;
import bg.wallet.www.project.services.QuoteService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuoteServiceImpl implements QuoteService {

    private final QuoteRepository quoteRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public QuoteServiceImpl(QuoteRepository quoteRepository, ModelMapper modelMapper) {
        this.quoteRepository = quoteRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<QuoteViewModel> findAll() {
        return this.quoteRepository.findAll().stream().map(q->this.modelMapper.map(q,QuoteViewModel.class)).collect(Collectors.toList());
    }

    @Override
    public QuoteViewModel findMain() {
        return this.modelMapper.map(this.quoteRepository.findQuoteByMain(true),QuoteViewModel.class);
    }

    @Override
    public void makeMain() {
        Quote quote = this.quoteRepository.findQuoteByMain(true);
        long count = this.quoteRepository.count();
        long nextId;

        if (quote.getId() == count) {
            nextId = 1;
        } else {
            nextId = quote.getId() + 1;
        }

        Quote nextMain = this.quoteRepository.findById(nextId).orElse(null);

        nextMain.setMain(true);
        quote.setMain(false);

        this.quoteRepository.save(quote);
        this.quoteRepository.save(nextMain);
    }

    public void seedQuotes() {
        long count = this.quoteRepository.count();
        if (count == 0) {

            Quote[] arrayQuotes = new Quote[5];
            arrayQuotes[0] = new Quote().setMain(true).setText("If you would be wealthy, think of saving as well as getting.").setAuthor("Benjamin Franklin");
            arrayQuotes[1] = new Quote().setMain(false).setText("Many folks think they aren’t good at earning money, when what they don’t know is how to use it.").setAuthor("Frank A. Clark");
            arrayQuotes[2] = new Quote().setMain(false).setText("Money is only a tool. It will take you wherever you wish, but it will not replace you as the driver.").setAuthor("Ayn Rand");
            arrayQuotes[3] = new Quote().setMain(false).setText("Money is a terrible master but an excellent servant.").setAuthor("P.T. Barnum");
            arrayQuotes[4] = new Quote().setMain(false).setText("You must gain control over your money or the lack of it will forever control you.").setAuthor("Dave Ramsey");

            Arrays.stream(arrayQuotes).forEach(this.quoteRepository::save);
        }
    }
}
