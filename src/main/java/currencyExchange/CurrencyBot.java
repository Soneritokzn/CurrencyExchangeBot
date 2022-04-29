package currencyExchange;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class CurrencyBot extends TelegramLongPollingBot  {


    @Override
    public String getBotUsername() {

        return "CurrencyCountTimBot";
    }

    @Override
    public String getBotToken() {

        return "5342923291:AAGRclqFcP1rViVFl4o8aG25l8c18hqBq90";
    }
    Parser parser = new Parser();
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            SendMessage message = new SendMessage();
            message.setChatId(update.getMessage().getChatId().toString());
            message.setText(String.valueOf(Parser.result));


            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }

        }
    }
}






