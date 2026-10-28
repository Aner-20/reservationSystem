-- CREATE EXTENSION abilita estensione postgreSQL
-- btree_gist permette di usare operatori di confronto = in indici GIST
CREATE EXTENSION IF NOT EXISTS btree_gist;

CREATE TABLE reservations (
    id UUID PRIMARY KEY,
    customer_id UUID NOT NULL,
    restaurant_id UUID NOT NULL,
    table_id UUID NOT NULL,

    start_time TIMESTAMPTZ NOT NULL,
    end_time TIMESTAMPTZ NOT NULL,

    status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    -- TIMESTAMPTZ gestisce anche i fusi orari
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),

    -- accetta solo prenotazioni in cui l'ora finale è successiva all'ora iniziale
    CHECK (end_time > start_time),
    -- 
    CHECK (status IN ('PENDING', 'CONFIRMED', 'CANCELLED', 'COMPLETED')),

    -- EXCLUDE crea un vincolo di esclusione: impedisce che due righe soddisfino contemporanemente determinate condizioni di conflitto
    -- using gist specifica il tipo di indice usato per applicare il vincolo
    -- si vuole evitare che due prenotazioni si riferiscano allo stesso tavolo
    -- abbiano intervalli temporali sovrapposti, siano entrambe secondo gli stati definiti nel vincolo
    
    EXCLUDE USING gist (
        -- table id with = significa che il conflitto si verifica quando due prenotazioni hanno lo stesso table_id
        table_id WITH =,
        -- tstzrange: funzione postgresql che costruisce un intervallo temporale a partire da due valori TIMESTAMPTZ
        -- [), [ -> include l'inizio, ) -> esclude la fine
        -- with && significa che due intervalli hanno almeno un elemento in comune, cioè si sovrappongono
        -- where status ... limita il vincolo alle prenotazioni con stato pending o confirmed
        tstzrange(start_time, end_time, '[)') WITH &&
    ) WHERE (status IN ('PENDING', 'CONFIRMED'))
);
