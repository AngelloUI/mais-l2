package parsing;

public class Factory {
    private enum TypeParser {
        SAX, STAX, DOM
    }

    private Factory() {
    }

    public static AbstractParseBuilder createMedicineBuilder(String typeParser) {
        TypeParser type = TypeParser.valueOf(typeParser.toUpperCase());
        switch (type) {
            case DOM -> {
                return new DomParse();
            }
            case STAX -> {
                return new StaxParse();
            }
            case SAX -> {
                return new SaxParse();
            }
            default -> throw new EnumConstantNotPresentException(
                    type.getDeclaringClass(), type.name());
        }
    }
    public static void main(String[] args) {
        String type = "dom"; // Тип парсера (SAX, STAX, DOM)
        AbstractParseBuilder builder = Factory.createMedicineBuilder(type);
        builder.buildSetMedicines("C:\\Users\\Дмитрий\\OneDrive\\Рабочий стол\\lab2\\medicins.xml"); // Путь к файлу XML
        System.out.println(builder.getMedicines()); // Получение и вывод списка медикаментов
    }
}
