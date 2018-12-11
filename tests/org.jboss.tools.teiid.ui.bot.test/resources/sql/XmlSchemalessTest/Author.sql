SELECT LASTNAME, FIRSTNAME 
FROM Books.AUTHORS, Books.BOOK_AUTHORS 
WHERE (INPUTS.isbn = ISBN) AND (Books.AUTHORS.AUTHOR_ID = Books.BOOK_AUTHORS.AUTHOR_ID)
ORDER BY LASTNAME ASC