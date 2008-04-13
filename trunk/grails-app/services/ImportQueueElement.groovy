class ImportQueueElement {
    Item item
    List<String> tagList
    // Special end of stream marker
    static final ImportQueueElement EOS = new ImportQueueElement()
}